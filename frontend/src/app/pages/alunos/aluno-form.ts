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
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-aluno-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, CardModule, ToastModule, FloatLabelModule, DatePickerModule, SelectModule, TableModule],
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
          <div class="col-span-12 md:col-span-6 lg:col-span-4">
            <p-select id="responsavelId" formControlName="responsavelId" [options]="responsaveis()" optionLabel="nome" optionValue="id" placeholder="Responsável Principal *" class="w-full" styleClass="w-full" />
            @if (form.get('responsavelId')?.hasError('required') && form.get('responsavelId')?.touched) { <small class="text-red-500 mt-1 block">Responsável Principal é obrigatório</small> }
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/alunos" />
        </div>
      </form>
    </div>

    @if (isEdit()) {
      <div class="card mt-6">
        <h3 class="m-0 mb-4 text-color font-bold text-xl">Outros Responsáveis / Vínculos</h3>
        <form [formGroup]="linkForm" (ngSubmit)="onLinkSubmit()" class="flex flex-col md:flex-row gap-4 mb-6">
          <div class="flex-1">
            <p-select formControlName="responsavelId" [options]="responsaveis()" optionLabel="nome" optionValue="id" placeholder="Selecione o Responsável" class="w-full" styleClass="w-full" />
          </div>
          <div class="flex-1">
            <p-select formControlName="parentesco" [options]="parentescoOptions" placeholder="Parentesco" class="w-full" styleClass="w-full" />
          </div>
          <div class="flex items-center gap-2">
            <input type="checkbox" id="permiteBuscar" formControlName="permiteBuscarEscola" />
            <label for="permiteBuscar">Pode buscar?</label>
          </div>
          <div class="flex items-center gap-2">
            <input type="checkbox" id="contatoEmergencia" formControlName="contatoEmergencia" />
            <label for="contatoEmergencia">Emergência?</label>
          </div>
          <p-button type="submit" label="Vincular" icon="pi pi-link" [disabled]="linkForm.invalid" />
        </form>

        <p-table [value]="responsaveisVinculados()" dataKey="responsavel.id">
          <ng-template #header><tr><th>Nome</th><th>Parentesco</th><th>Pode Buscar</th><th>Emergência</th></tr></ng-template>
          <ng-template #body let-item>
            <tr>
              <td>{{ item.responsavel.nome }}</td>
              <td>{{ item.parentesco }}</td>
              <td>{{ item.permiteBuscarEscola ? 'Sim' : 'Não' }}</td>
              <td>{{ item.contatoEmergencia ? 'Sim' : 'Não' }}</td>
            </tr>
          </ng-template>
          <ng-template #emptymessage><tr><td colspan="4" class="text-center p-4">Nenhum responsável adicional vinculado.</td></tr></ng-template>
        </p-table>
      </div>
    }
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
    responsavelId: [null as string | null, Validators.required]
  });
  isEdit = signal(false);
  loading = signal(false);
  recordId = signal<string | null>(null);
  responsaveis = signal<any[]>([]);
  responsaveisVinculados = signal<any[]>([]);

  parentescoOptions = ['PAI', 'MAE', 'AVO_AVO', 'TIO_TIA', 'IRMAO_IRMA', 'OUTRO'];

  linkForm = this.fb.group({
    responsavelId: ['', Validators.required],
    parentesco: ['PAI', Validators.required],
    permiteBuscarEscola: [false],
    contatoEmergencia: [false]
  });

  ngOnInit() {
    this.carregarResponsaveis();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit.set(true);
      this.recordId.set(id);
      this.http.get<any>(`${this.API}/${id}`).subscribe({
        next: (data) => {
          let dn: Date | null = null;
          if (data.dataNascimento) { const [y, m, d] = data.dataNascimento.split('-').map(Number); dn = new Date(y, m - 1, d); }
          this.form.patchValue({ ...data, dataNascimento: dn });
        },
      });
      this.carregarResponsaveisVinculados(id);
    }
  }

  carregarResponsaveis() {
    this.http.get<any[]>(`${environment.apiUrl}/responsaveis/todos`).subscribe({
      next: (data) => this.responsaveis.set(data)
    });
  }

  carregarResponsaveisVinculados(alunoId: string) {
    this.http.get<any[]>(`${this.API}/${alunoId}/responsaveis`).subscribe({
      next: (data) => this.responsaveisVinculados.set(data)
    });
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha os campos obrigatórios' }); return; }
    this.loading.set(true);
    const data: any = { ...this.form.value };
    if (data.dataNascimento instanceof Date) {
      const d = data.dataNascimento;
      data.dataNascimento = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`;
    }
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, data) : this.http.post(this.API, data);
    req$.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Aluno atualizado' : 'Aluno cadastrado' }); setTimeout(() => this.router.navigate(['/alunos']), 1000); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }

  onLinkSubmit() {
    if (this.linkForm.invalid || !this.recordId()) return;
    const body = this.linkForm.value;
    this.http.post(`${this.API}/${this.recordId()}/responsaveis`, body).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Vínculo adicionado' });
        this.linkForm.reset({ parentesco: 'PAI', permiteBuscarEscola: false, contatoEmergencia: false });
        this.carregarResponsaveisVinculados(this.recordId()!);
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao vincular' });
      }
    });
  }
}
