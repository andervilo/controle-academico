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
import { DatePickerModule } from 'primeng/datepicker';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-aluno-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, CardModule, ToastModule, FloatLabelModule, DatePickerModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/alunos" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Editar Aluno' : 'Novo Aluno' }}</h2>
    </div>
    <div class="card">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-12 gap-6">
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nome" class="w-full" /><label for="nome">Nome *</label></p-floatlabel>
            @if (form.get('nome')?.hasError('required') && form.get('nome')?.touched) { <small class="text-red-500 mt-1 block">Nome é obrigatório</small> }
          </div>
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-floatlabel variant="on"><input pInputText id="cpf" formControlName="cpf" class="w-full" /><label for="cpf">CPF *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-floatlabel variant="on">
              <p-datepicker id="dataNascimento" formControlName="dataNascimento" dateFormat="dd/mm/yy" [showIcon]="true" class="w-full" />
              <label for="dataNascimento">Data de Nascimento *</label>
            </p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-floatlabel variant="on"><input pInputText id="email" formControlName="email" class="w-full" /><label for="email">E-mail *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-floatlabel variant="on"><input pInputText id="telefone" formControlName="telefone" class="w-full" /><label for="telefone">Telefone *</label></p-floatlabel>
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/alunos" />
        </div>
      </form>
    </div>
  `,
})
export class AlunoFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/alunos`;

  form = this.fb.group({
    nome: ['', Validators.required],
    cpf: ['', Validators.required],
    dataNascimento: [null as Date | null, Validators.required],
    email: ['', [Validators.required, Validators.email]],
    telefone: ['', Validators.required],
  });
  isEdit = signal(false);
  loading = signal(false);
  recordId = signal<string | null>(null);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit.set(true);
      this.recordId.set(id);
      this.http.get<any>(`${this.API}/${id}`).subscribe({
        next: (data) => this.form.patchValue({ ...data, dataNascimento: data.dataNascimento ? new Date(data.dataNascimento) : null }),
      });
    }
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha os campos obrigatórios' }); return; }
    this.loading.set(true);
    const data: any = { ...this.form.value };
    if (data.dataNascimento instanceof Date) data.dataNascimento = data.dataNascimento.toISOString().split('T')[0];
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, data) : this.http.post(this.API, data);
    req$.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Aluno atualizado' : 'Aluno cadastrado' }); setTimeout(() => this.router.navigate(['/alunos']), 1000); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }
}
