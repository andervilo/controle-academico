import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { environment } from '@/environments/environment';
import { MultiSelectModule } from 'primeng/multiselect';
import { forkJoin, map, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-professor-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, CardModule, ToastModule, FloatLabelModule, MultiSelectModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/professores" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Editar Professor' : 'Novo Professor' }}</h2>
    </div>
    <div class="card">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-12 gap-6">
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nome" class="w-full" /><label for="nome">Nome *</label></p-floatlabel>
            @if (form.get('nome')?.hasError('required') && form.get('nome')?.touched) { <small class="text-red-500 mt-1 block">Nome é obrigatório</small> }
          </div>
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on"><input pInputText id="cpf" formControlName="cpf" class="w-full" /><label for="cpf">CPF *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on"><input pInputText id="email" formControlName="email" class="w-full" /><label for="email">E-mail *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6">
            <p-floatlabel variant="on"><input pInputText id="telefone" formControlName="telefone" class="w-full" /><label for="telefone">Telefone *</label></p-floatlabel>
          </div>
          <div class="col-span-12">
            <p-floatlabel variant="on">
              <p-multiselect id="disciplinas" formControlName="disciplinaIds" [options]="disciplinas()" optionLabel="nome" optionValue="id" [filter]="true" class="w-full" display="chip" />
              <label for="disciplinas">Disciplinas Habilitadas</label>
            </p-floatlabel>
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/professores" />
        </div>
      </form>
    </div>
  `,
})
export class ProfessorFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/professores`;

  form = this.fb.group({
    nome: ['', Validators.required],
    cpf: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    telefone: ['', Validators.required],
    disciplinaIds: [[] as string[]]
  });
  isEdit = signal(false); loading = signal(false); recordId = signal<string | null>(null);
  disciplinas = signal<any[]>([]);

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/disciplinas/todos`).subscribe({ next: (res) => this.disciplinas.set(res) });
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit.set(true);
      this.recordId.set(id);
      forkJoin({
        professor: this.http.get<any>(`${this.API}/${id}`),
        disciplinas: this.http.get<any[]>(`${this.API}/${id}/disciplinas`).pipe(catchError(() => of([])))
      }).subscribe({
        next: (data) => {
          this.form.patchValue({
            ...data.professor,
            disciplinaIds: data.disciplinas.map(d => d.id)
          });
        }
      });
    }
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha os campos obrigatórios' }); return; }
    this.loading.set(true);
    const { disciplinaIds, ...professorData } = this.form.value;
    const req$ = this.isEdit() ? this.http.put<any>(`${this.API}/${this.recordId()}`, professorData) : this.http.post<any>(this.API, professorData);

    req$.subscribe({
      next: (prof) => {
        const id = this.isEdit() ? this.recordId() : prof.id;
        // Save disciplines
        this.http.get<any[]>(`${this.API}/${id}/disciplinas`).pipe(
          catchError(() => of([])),
          map(curr => {
            const currIds = curr.map(d => d.id);
            const toAdd = (disciplinaIds || []).filter(dId => !currIds.includes(dId));
            const toRemove = currIds.filter(dId => !(disciplinaIds || []).includes(dId));
            return { toAdd, toRemove };
          })
        ).subscribe(diff => {
          const addActions = diff.toAdd.map(dId => this.http.post(`${this.API}/${id}/disciplinas/${dId}`, {}));
          const removeActions = diff.toRemove.map(dId => this.http.delete(`${this.API}/${id}/disciplinas/${dId}`));

          if (addActions.length === 0 && removeActions.length === 0) {
            this.finishSubmit();
          } else {
            forkJoin([...addActions, ...removeActions]).subscribe(() => this.finishSubmit());
          }
        });
      },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }

  private finishSubmit() {
    this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Professor atualizado' : 'Professor cadastrado' });
    setTimeout(() => this.router.navigate(['/professores']), 1000);
  }
}
