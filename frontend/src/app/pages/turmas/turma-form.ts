import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-turma-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, SelectModule, CardModule, ToastModule, FloatLabelModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/turmas" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Editar Turma' : 'Nova Turma' }}</h2>
    </div>
    <div class="card">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-12 gap-6">
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nome" class="w-full" /><label for="nome">Nome *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on">
              <p-select id="cursoId" formControlName="cursoId" [options]="cursos()" optionLabel="nome" optionValue="id" [filter]="true" filterBy="nome" class="w-full" />
              <label for="cursoId">Curso *</label>
            </p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on">
              <p-select id="anoLetivoId" formControlName="anoLetivoId" [options]="anosLetivos()" optionLabel="ano" optionValue="id" class="w-full" />
              <label for="anoLetivoId">Ano Letivo *</label>
            </p-floatlabel>
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/turmas" />
        </div>
      </form>
    </div>
  `,
})
export class TurmaFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/turmas`;

  form = this.fb.group({ nome: ['', Validators.required], cursoId: ['', Validators.required], anoLetivoId: ['', Validators.required] });
  isEdit = signal(false); loading = signal(false); recordId = signal<string | null>(null);
  cursos = signal<any[]>([]); anosLetivos = signal<any[]>([]);

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/cursos`).subscribe({ next: (r) => this.cursos.set(r) });
    this.http.get<any[]>(`${environment.apiUrl}/anos-letivos`).subscribe({ next: (r) => this.anosLetivos.set(r) });
    const id = this.route.snapshot.paramMap.get('id');
    if (id) { this.isEdit.set(true); this.recordId.set(id); this.http.get<any>(`${this.API}/${id}`).subscribe({ next: (d) => this.form.patchValue(d) }); }
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading.set(true);
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, this.form.value) : this.http.post(this.API, this.form.value);
    req$.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Turma atualizada' : 'Turma cadastrada' }); setTimeout(() => this.router.navigate(['/turmas']), 1000); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }
}
