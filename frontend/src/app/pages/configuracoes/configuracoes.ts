import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TabsModule } from 'primeng/tabs';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { InputNumberModule } from 'primeng/inputnumber';
import { FileUploadModule } from 'primeng/fileupload';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-configuracoes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ButtonModule, InputTextModule, CardModule, ToastModule, FloatLabelModule, TabsModule, TableModule, DialogModule, SelectModule, TagModule, InputNumberModule, FileUploadModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="mb-6">
      <h2 class="m-0 text-color font-bold text-2xl">Configurações do Sistema</h2>
    </div>

    <p-tabs [value]="0">
      <p-tablist>
        <p-tab [value]="0"><i class="pi pi-building mr-2"></i>Instituição</p-tab>
        <p-tab [value]="1"><i class="pi pi-users mr-2"></i>Equipe Gestora</p-tab>
        <p-tab [value]="2"><i class="pi pi-money-bill mr-2"></i>Financeiro por Ano</p-tab>
      </p-tablist>
      <p-tabpanels>
        <!-- ABA 1: INSTITUIÇÃO -->
        <p-tabpanel [value]="0">
          <div class="card mt-4">
            <form [formGroup]="escolaForm" (ngSubmit)="salvarEscola()">
              <div class="grid grid-cols-12 gap-6">
                <div class="col-span-12 md:col-span-8">
                  <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nomeInstituicao" class="w-full" /><label for="nome">Nome da Instituição *</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-2">
                  <p-floatlabel variant="on"><input pInputText id="cnpj" formControlName="cnpj" class="w-full" /><label for="cnpj">CNPJ</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-2">
                  <p-floatlabel variant="on"><input pInputText id="inep" formControlName="inep" class="w-full" /><label for="inep">INEP</label></p-floatlabel>
                </div>
                
                <div class="col-span-12 md:col-span-2">
                  <p-floatlabel variant="on"><input pInputText id="cep" formControlName="cep" class="w-full" /><label for="cep">CEP</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-8">
                  <p-floatlabel variant="on"><input pInputText id="logradouro" formControlName="logradouro" class="w-full" /><label for="logradouro">Logradouro</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-2">
                  <p-floatlabel variant="on"><input pInputText id="numero" formControlName="numero" class="w-full" /><label for="numero">Número</label></p-floatlabel>
                </div>

                <div class="col-span-12 md:col-span-4">
                  <p-floatlabel variant="on"><input pInputText id="bairro" formControlName="bairro" class="w-full" /><label for="bairro">Bairro</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-6">
                  <p-floatlabel variant="on"><input pInputText id="cidade" formControlName="cidade" class="w-full" /><label for="cidade">Cidade</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-2">
                  <p-floatlabel variant="on"><input pInputText id="uf" formControlName="uf" class="w-full" /><label for="uf">UF</label></p-floatlabel>
                </div>

                <div class="col-span-12 md:col-span-6">
                  <p-floatlabel variant="on"><input pInputText id="telefone" formControlName="telefone" class="w-full" /><label for="telefone">Telefone</label></p-floatlabel>
                </div>
                <div class="col-span-12 md:col-span-6">
                  <p-floatlabel variant="on"><input pInputText id="email" formControlName="emailContato" class="w-full" /><label for="email">E-mail de Contato</label></p-floatlabel>
                </div>
                
                <div class="col-span-12">
                   <label class="block mb-2 font-bold">Logotipo da Escola</label>
                   <div class="flex items-center gap-4">
                     <div *ngIf="escolaForm.get('logotipoBase64')?.value" class="border p-2 rounded surface-border">
                       <img [src]="escolaForm.get('logotipoBase64')?.value" alt="Logo" style="max-height: 100px;">
                     </div>
                     <p-fileupload mode="basic" chooseLabel="Escolher Logo" accept="image/*" maxFileSize="1000000" (onSelect)="onLogoSelect($event)" customUpload="true" auto="true" />
                     <p-button *ngIf="escolaForm.get('logotipoBase64')?.value" icon="pi pi-trash" [text]="true" severity="danger" (click)="escolaForm.get('logotipoBase64')?.setValue('')" />
                   </div>
                </div>
              </div>
              <div class="flex gap-3 mt-6">
                <p-button label="Salvar Dados da Escola" icon="pi pi-check" type="submit" [loading]="loading()" />
              </div>
            </form>
          </div>
        </p-tabpanel>

        <!-- ABA 2: EQUIPE -->
        <p-tabpanel [value]="1">
          <div class="card mt-4">
            <div class="flex justify-between items-center mb-4">
              <h3 class="m-0 font-bold text-xl">Equipe Administrativa</h3>
              <p-button label="Novo Membro" icon="pi pi-plus" severity="success" (click)="abrirModalEquipe()" />
            </div>
            <p-table [value]="equipe()" [tableStyle]="{ 'min-width': '50rem' }">
              <ng-template #header>
                <tr><th>Nome</th><th>Cargo</th><th>E-mail</th><th>CPF</th><th>Status</th><th style="width: 5rem">Ações</th></tr>
              </ng-template>
              <ng-template #body let-membro>
                <tr>
                  <td>{{ membro.nome }}</td>
                  <td><p-tag [value]="membro.cargo" severity="secondary" /></td>
                  <td>{{ membro.email }}</td>
                  <td>{{ membro.cpf }}</td>
                  <td><p-tag [value]="membro.ativo ? 'Ativo' : 'Inativo'" [severity]="membro.ativo ? 'success' : 'danger'" /></td>
                  <td>
                    <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="removerEquipe(membro.id)" />
                  </td>
                </tr>
              </ng-template>
            </p-table>
          </div>
        </p-tabpanel>

        <!-- ABA 3: FINANCEIRO -->
        <p-tabpanel [value]="2">
          <div class="card mt-4">
            <div class="grid grid-cols-12 gap-4 mb-6">
              <div class="col-span-12 md:col-span-4">
                <p-floatlabel variant="on">
                  <p-select [options]="anosLetivos()" [(ngModel)]="anoSelecionado" optionLabel="ano" optionValue="id" (onChange)="carregarPrecos()" class="w-full" />
                  <label>Selecione o Ano Letivo</label>
                </p-floatlabel>
              </div>
              <div class="col-span-12 md:col-span-8 flex justify-end gap-2">
                 <p-button label="Configurar Todos os Cursos" icon="pi pi-sliders-h" [disabled]="!anoSelecionado" (click)="abrirConfigTodosCursos()" />
              </div>
            </div>

            <p-table [value]="precosCursos()" [tableStyle]="{ 'min-width': '50rem' }">
              <ng-template #header>
                <tr><th>Curso</th><th>Matrícula</th><th>Mensalidade</th><th>Meses</th><th>Vencimento</th><th style="width: 5rem">Ações</th></tr>
              </ng-template>
              <ng-template #body let-item>
                <tr>
                  <td class="font-bold">{{ item.cursoNome }}</td>
                  <td>{{ item.valorMatricula | currency:'BRL' }}</td>
                  <td>{{ item.valorMensalidade | currency:'BRL' }}</td>
                  <td>{{ item.quantidadeMeses }} meses</td>
                  <td>Dia {{ item.diaVencimentoPadrao }}</td>
                  <td>
                    <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" (click)="editarPreco(item)" />
                  </td>
                </tr>
              </ng-template>
              <ng-template #emptymessage>
                <tr><td colspan="6" class="text-center p-6 text-muted-color">Nenhum valor configurado para este ano ou não há cursos cadastrados.</td></tr>
              </ng-template>
            </p-table>
          </div>
        </p-tabpanel>
      </p-tabpanels>
    </p-tabs>

    <!-- Modal Equipe -->
    <p-dialog [header]="'Novo Membro da Equipe'" [(visible)]="showModalEquipe" [modal]="true" [style]="{width: '30vw'}">
      <form [formGroup]="equipeForm" class="flex flex-col gap-4 mt-2">
        <p-floatlabel variant="on"><input pInputText formControlName="nome" class="w-full" /><label>Nome Completo *</label></p-floatlabel>
        <p-floatlabel variant="on"><input pInputText formControlName="cpf" class="w-full" /><label>CPF</label></p-floatlabel>
        <p-floatlabel variant="on"><input pInputText formControlName="email" class="w-full" /><label>E-mail</label></p-floatlabel>
        <p-floatlabel variant="on">
          <p-select formControlName="cargo" [options]="cargos" class="w-full" />
          <label>Cargo *</label>
        </p-floatlabel>
      </form>
      <ng-template #footer>
        <p-button label="Cancelar" [text]="true" severity="secondary" (click)="showModalEquipe = false" />
        <p-button label="Salvar" icon="pi pi-check" (click)="salvarEquipe()" [disabled]="equipeForm.invalid" />
      </ng-template>
    </p-dialog>

    <!-- Modal Preço -->
    <p-dialog [header]="'Configurar Valor: ' + precoSelecionado?.cursoNome" [(visible)]="showModalPreco" [modal]="true" [style]="{width: '30vw'}">
      <form [formGroup]="precoForm" class="flex flex-col gap-4 mt-2">
        <div class="flex flex-col gap-1">
          <label class="font-bold text-sm">Valor da Matrícula</label>
          <p-inputnumber formControlName="valorMatricula" mode="currency" currency="BRL" locale="pt-BR" class="w-full" />
        </div>
        <div class="flex flex-col gap-1">
          <label class="font-bold text-sm">Valor da Mensalidade</label>
          <p-inputnumber formControlName="valorMensalidade" mode="currency" currency="BRL" locale="pt-BR" class="w-full" />
        </div>
        <div class="flex flex-col gap-1">
          <label class="font-bold text-sm">Quantidade de Meses</label>
          <p-inputnumber formControlName="quantidadeMeses" [min]="1" [max]="24" class="w-full" />
        </div>
        <div class="flex flex-col gap-1">
          <label class="font-bold text-sm">Dia de Vencimento Padrão</label>
          <p-inputnumber formControlName="diaVencimentoPadrao" [min]="1" [max]="31" class="w-full" />
        </div>
      </form>
      <ng-template #footer>
        <p-button label="Cancelar" [text]="true" severity="secondary" (click)="showModalPreco = false" />
        <p-button label="Salvar Preço" icon="pi pi-check" (click)="salvarPreco()" [disabled]="precoForm.invalid" />
      </ng-template>
    </p-dialog>
  `
})
export class ConfiguracoesComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/config`;

  escolaForm = this.fb.group({
    id: [null],
    nomeInstituicao: ['', Validators.required],
    cnpj: [''], inep: [''], cep: [''], logradouro: [''], numero: [''], bairro: [''], cidade: [''], uf: [''], telefone: [''], emailContato: [''], logotipoBase64: ['']
  });

  equipeForm = this.fb.group({ nome: ['', Validators.required], cpf: [''], email: ['', Validators.email], cargo: ['', Validators.required], ativo: [true] });
  precoForm = this.fb.group({ valorMatricula: [0, Validators.required], valorMensalidade: [0, Validators.required], quantidadeMeses: [12, Validators.required], diaVencimentoPadrao: [10, Validators.required] });

  loading = signal(false);
  equipe = signal<any[]>([]);
  anosLetivos = signal<any[]>([]);
  cursos = signal<any[]>([]);
  precosCursos = signal<any[]>([]);
  anoSelecionado: string | null = null;

  showModalEquipe = false;
  showModalPreco = false;
  precoSelecionado: any = null;
  cargos = ['DIRETOR', 'SECRETARIO', 'COORDENADOR', 'MONITOR', 'OUTRO'];

  ngOnInit() {
    this.carregarDadosEscola();
    this.carregarEquipe();
    this.carregarAnosCursos();
  }

  carregarDadosEscola() {
    this.http.get<any>(`${this.API}/escola`).subscribe(res => this.escolaForm.patchValue(res));
  }

  carregarEquipe() {
    this.http.get<any[]>(`${this.API}/equipe`).subscribe(res => this.equipe.set(res));
  }

  carregarAnosCursos() {
    this.http.get<any[]>(`${environment.apiUrl}/anos-letivos/todos`).subscribe(res => {
      this.anosLetivos.set(res);
      if (res.length > 0) {
        this.anoSelecionado = res[0].id;
        this.carregarPrecos();
      }
    });
    this.http.get<any[]>(`${environment.apiUrl}/cursos/todos`).subscribe(res => this.cursos.set(res));
  }

  carregarPrecos() {
    if (!this.anoSelecionado) return;
    this.http.get<any[]>(`${this.API}/financeiro?anoLetivoId=${this.anoSelecionado}`).subscribe(res => {
      // Mesclar cursos totais com precos existentes para garantir que todos apareçam na lista
      const merge = this.cursos().map(c => {
        const preco = res.find(p => p.cursoId === c.id);
        return preco ? preco : { cursoId: c.id, cursoNome: c.nome, valorMatricula: 0, valorMensalidade: 0, quantidadeMeses: 12, diaVencimentoPadrao: 10 };
      });
      this.precosCursos.set(merge);
    });
  }

  salvarEscola() {
    if (this.escolaForm.invalid) return;
    this.loading.set(true);
    this.http.put(`${this.API}/escola`, this.escolaForm.value).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Dados da escola atualizados' });
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  onLogoSelect(event: any) {
    const file = event.files[0];
    const reader = new FileReader();
    reader.onload = (e: any) => this.escolaForm.get('logotipoBase64')?.setValue(e.target.result);
    reader.readAsDataURL(file);
  }

  abrirModalEquipe() {
    this.equipeForm.reset({ ativo: true, cargo: 'OUTRO' });
    this.showModalEquipe = true;
  }

  salvarEquipe() {
    this.http.post(`${this.API}/equipe`, this.equipeForm.value).subscribe(() => {
      this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Membro cadastrado' });
      this.showModalEquipe = false;
      this.carregarEquipe();
    });
  }

  removerEquipe(id: string) {
    this.http.delete(`${this.API}/equipe/${id}`).subscribe(() => {
      this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Removido da equipe' });
      this.carregarEquipe();
    });
  }

  editarPreco(item: any) {
    this.precoSelecionado = item;
    this.precoForm.patchValue(item);
    this.showModalPreco = true;
  }

  salvarPreco() {
    const data = { ...this.precoForm.value, cursoId: this.precoSelecionado.cursoId, anoLetivoId: this.anoSelecionado };
    this.http.post(`${this.API}/financeiro`, data).subscribe(() => {
      this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Preço atualizado' });
      this.showModalPreco = false;
      this.carregarPrecos();
    });
  }

  abrirConfigTodosCursos() {
    this.messageService.add({ severity: 'info', summary: 'Aviso', detail: 'Funcionalidade em desenvolvimento' });
  }
}
