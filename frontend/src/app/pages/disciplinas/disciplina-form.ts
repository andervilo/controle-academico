import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-disciplina-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, InputNumberModule, CardModule, ToastModule, FloatLabelModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/disciplinas" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Editar Disciplina' : 'Nova Disciplina' }}</h2>
    </div>
    <div class="card">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-12 gap-6">
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nome" class="w-full" /><label for="nome">Nome *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on">
              <p-inputnumber id="cargaHoraria" formControlName="cargaHoraria" [min]="1" class="w-full" />
              <label for="cargaHoraria">Carga Horária (h) *</label>
            </p-floatlabel>
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/disciplinas" />
        </div>
      </form>
    </div>
  `,
})
export class DisciplinaFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/disciplinas`;

  form = this.fb.group({ nome: ['', Validators.required], cargaHoraria: [null as number | null, Validators.required] });
  isEdit = signal(false); loading = signal(false); recordId = signal<string | null>(null);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) { this.isEdit.set(true); this.recordId.set(id); this.http.get<any>(`${this.API}/${id}`).subscribe({ next: (data) => this.form.patchValue(data) }); }
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha os campos obrigatórios' }); return; }
    this.loading.set(true);
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, this.form.value) : this.http.post(this.API, this.form.value);
    req$.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Disciplina atualizada' : 'Disciplina cadastrada' }); setTimeout(() => this.router.navigate(['/disciplinas']), 1000); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }
}
