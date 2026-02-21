import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TabsModule } from 'primeng/tabs';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';
import { TagModule } from 'primeng/tag';
import { environment } from '@/environments/environment';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-turma-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule, ButtonModule, InputTextModule, SelectModule, CardModule, ToastModule, FloatLabelModule, TabsModule, TableModule, DialogModule, MultiSelectModule, TagModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/turmas" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Gerenciar Turma' : 'Nova Turma' }}</h2>
    </div>

    <p-tabs [value]="0">
      <p-tablist>
        <p-tab [value]="0"><i class="pi pi-info-circle mr-2"></i>Dados Básicos</p-tab>
        <p-tab [value]="1" [disabled]="!isEdit()"><i class="pi pi-users mr-2"></i>Alunos</p-tab>
        <p-tab [value]="2" [disabled]="!isEdit()"><i class="pi pi-calendar mr-2"></i>Grade Horária</p-tab>
      </p-tablist>
      <p-tabpanels>
        <!-- ABA 1: DADOS BÁSICOS -->
        <p-tabpanel [value]="0">
          <div class="card mt-4">
            <form [formGroup]="form" (ngSubmit)="onSubmit()">
              <div class="grid grid-cols-12 gap-6">
                <div class="col-span-12 md:col-span-4">
                  <p-floatlabel variant="on"><input pInputText id="nome" formControlName="nome" class="w-full" /><label for="nome">Nome da Turma *</label></p-floatlabel>
                  @if (form.get('nome')?.hasError('required') && form.get('nome')?.touched) { <small class="text-red-500 mt-1 block">Nome é obrigatório</small> }
                </div>
                <div class="col-span-12 md:col-span-4">
                  <p-floatlabel variant="on">
                    <p-select id="cursoId" formControlName="cursoId" [options]="cursos()" optionLabel="nome" optionValue="id" [filter]="true" filterBy="nome" class="w-full" (onChange)="onCursoChange()" />
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
                <p-button [label]="isEdit() ? 'Salvar Alterações' : 'Criar Turma'" icon="pi pi-check" type="submit" [loading]="loading()" />
                <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/turmas" />
              </div>
            </form>
          </div>
        </p-tabpanel>

        <!-- ABA 2: ALUNOS -->
        <p-tabpanel [value]="1">
          <div class="card mt-4">
            <div class="flex justify-between items-center mb-4">
              <h3 class="m-0 font-bold text-xl">Alunos Matriculados</h3>
              <p-button label="Matricular Alunos" icon="pi pi-user-plus" severity="success" (click)="showAddAlunoDialog = true" />
            </div>
            <p-table [value]="alunosMatriculados()" [tableStyle]="{ 'min-width': '50rem' }">
              <ng-template #header>
                <tr><th>Matrícula</th><th>Nome</th><th>E-mail</th><th style="width: 5rem">Ações</th></tr>
              </ng-template>
              <ng-template #body let-aluno>
                <tr>
                  <td class="font-bold">{{ aluno.matricula }}</td>
                  <td>{{ aluno.nome }}</td>
                  <td>{{ aluno.email }}</td>
                  <td>
                    <p-button icon="pi pi-user-minus" [rounded]="true" [text]="true" severity="danger" (click)="removerAluno(aluno)" />
                  </td>
                </tr>
              </ng-template>
              <ng-template #emptymessage>
                <tr><td colspan="4" class="text-center p-6 text-muted-color">Nenhum aluno matriculado nesta turma.</td></tr>
              </ng-template>
            </p-table>
          </div>
        </p-tabpanel>

        <!-- ABA 3: GRADE HORÁRIA -->
        <p-tabpanel [value]="2">
          <div class="card mt-4">
            <h3 class="font-bold text-xl mb-4">Atribuir Disciplina e Professor</h3>
            <form [formGroup]="gradeForm" (ngSubmit)="onGradeSubmit()" class="grid grid-cols-12 gap-4 mb-6 p-4 border rounded-border surface-border">
              <div class="col-span-12 md:col-span-3">
                <p-floatlabel variant="on">
                  <p-select formControlName="disciplinaId" [options]="disciplinasCurso()" optionLabel="nome" optionValue="id" class="w-full" />
                  <label>Disciplina *</label>
                </p-floatlabel>
              </div>
              <div class="col-span-12 md:col-span-3">
                <p-floatlabel variant="on">
                  <p-select formControlName="professorId" [options]="professores()" optionLabel="nome" optionValue="id" class="w-full" />
                  <label>Professor *</label>
                </p-floatlabel>
              </div>
              <div class="col-span-12 md:col-span-2">
                <p-floatlabel variant="on">
                  <p-select formControlName="diaSemana" [options]="diasSemana" class="w-full" />
                  <label>Dia *</label>
                </p-floatlabel>
              </div>
              <div class="col-span-12 md:col-span-2 flex gap-2">
                <p-floatlabel variant="on"><input pInputText formControlName="horaInicio" placeholder="08:00" class="w-full" /><label>Início</label></p-floatlabel>
                <p-floatlabel variant="on"><input pInputText formControlName="horaFim" placeholder="10:00" class="w-full" /><label>Fim</label></p-floatlabel>
              </div>
              <div class="col-span-12 md:col-span-2">
                <p-button type="submit" label="Adicionar" icon="pi pi-plus" class="w-full" [disabled]="gradeForm.invalid" />
              </div>
            </form>

            <h3 class="font-bold text-xl mb-4">Grade da Turma</h3>
            <p-table [value]="gradeHoraria()" [tableStyle]="{ 'min-width': '50rem' }">
              <ng-template #header>
                <tr><th>Dia</th><th>Horário</th><th>Disciplina</th><th>Professor</th><th style="width: 5rem">Ações</th></tr>
              </ng-template>
              <ng-template #body let-item>
                <tr>
                  <td><p-tag [value]="item.diaSemana" severity="info" /></td>
                  <td>{{ item.horaInicio }} - {{ item.horaFim }}</td>
                  <td class="font-bold">{{ item.disciplinaNome }}</td>
                  <td>{{ item.professorNome }}</td>
                  <td>
                    <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="removerAtribuicao(item.id)" />
                  </td>
                </tr>
              </ng-template>
              <ng-template #emptymessage>
                <tr><td colspan="5" class="text-center p-6 text-muted-color">Nenhuma disciplina atribuída.</td></tr>
              </ng-template>
            </p-table>
          </div>
        </p-tabpanel>
      </p-tabpanels>
    </p-tabs>

    <p-dialog header="Matricular Alunos" [(visible)]="showAddAlunoDialog" [modal]="true" [style]="{width: '50vw'}" [draggable]="false" [resizable]="false">
      <div class="flex flex-col gap-4">
        <p class="text-muted-color">Selecione os alunos abaixo para matricular nesta turma.</p>
        <p-multiselect [options]="alunosDisponiveis()" [(ngModel)]="selectedAlunosIds" optionLabel="nome" optionValue="id" placeholder="Selecione os Alunos" class="w-full" styleClass="w-full" [filter]="true" [maxSelectedLabels]="3" display="chip" />
      </div>
      <ng-template #footer>
        <p-button label="Cancelar" icon="pi pi-times" [text]="true" severity="secondary" (click)="showAddAlunoDialog = false" />
        <p-button label="Confirmar Matrícula" icon="pi pi-check" (click)="matricularAlunos()" [disabled]="selectedAlunosIds.length === 0" />
      </ng-template>
    </p-dialog>
  `,
  styles: [`
    :host ::ng-deep .p-tabpanel { padding: 0; }
  `]
})
export class TurmaFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/turmas`;

  form = this.fb.group({
    nome: ['', Validators.required],
    cursoId: ['', Validators.required],
    anoLetivoId: ['', Validators.required]
  });

  gradeForm = this.fb.group({
    disciplinaId: ['', Validators.required],
    professorId: ['', Validators.required],
    diaSemana: ['SEGUNDA', Validators.required],
    horaInicio: ['', [Validators.required, Validators.pattern(/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/)]],
    horaFim: ['', [Validators.required, Validators.pattern(/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/)]]
  });

  isEdit = signal(false);
  loading = signal(false);
  recordId = signal<string | null>(null);

  cursos = signal<any[]>([]);
  anosLetivos = signal<any[]>([]);
  alunosMatriculados = signal<any[]>([]);
  alunosDisponiveis = signal<any[]>([]);
  gradeHoraria = signal<any[]>([]);
  disciplinasCurso = signal<any[]>([]);
  professores = signal<any[]>([]);

  showAddAlunoDialog = false;
  selectedAlunosIds: string[] = [];
  diasSemana = ['SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO'];

  ngOnInit() {
    this.carregarDadosIniciais();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit.set(true);
      this.recordId.set(id);
      this.carregarTurma(id);
    }
  }

  carregarDadosIniciais() {
    this.http.get<any[]>(`${environment.apiUrl}/cursos`).subscribe(r => this.cursos.set(r));
    this.http.get<any[]>(`${environment.apiUrl}/anos-letivos`).subscribe(r => this.anosLetivos.set(r));
    this.http.get<any[]>(`${environment.apiUrl}/professores`).subscribe(r => this.professores.set(r));
  }

  carregarTurma(id: string) {
    this.http.get<any>(`${this.API}/${id}`).subscribe(d => {
      this.form.patchValue(d);
      this.carregarDisciplinasDoCurso(d.cursoId);
    });
    this.carregarAlunos(id);
    this.carregarGrade(id);
  }

  carregarAlunos(id: string) {
    this.http.get<any[]>(`${this.API}/${id}/alunos`).subscribe(r => this.alunosMatriculados.set(r));
    this.http.get<any[]>(`${this.API}/${id}/alunos-disponiveis`).subscribe(r => this.alunosDisponiveis.set(r));
  }

  carregarGrade(id: string) {
    this.http.get<any[]>(`${this.API}/${id}/disciplinas-professores`).subscribe(r => this.gradeHoraria.set(r));
  }

  carregarDisciplinasDoCurso(cursoId: string) {
    this.http.get<any[]>(`${environment.apiUrl}/cursos/${cursoId}/disciplinas`).subscribe(r => this.disciplinasCurso.set(r));
  }

  onCursoChange() {
    const cursoId = this.form.get('cursoId')?.value;
    if (cursoId) this.carregarDisciplinasDoCurso(cursoId);
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading.set(true);
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, this.form.value) : this.http.post<any>(this.API, this.form.value);
    req$.subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Turma atualizada' : 'Turma criada' });
        if (!this.isEdit()) {
          setTimeout(() => this.router.navigate(['/turmas', res.id, 'editar']), 1000);
        } else {
          this.loading.set(false);
        }
      },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }

  matricularAlunos() {
    if (this.selectedAlunosIds.length === 0) return;
    this.http.post(`${this.API}/${this.recordId()}/alunos/lote`, { alunoIds: this.selectedAlunosIds }).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Alunos matriculados' });
        this.showAddAlunoDialog = false;
        this.selectedAlunosIds = [];
        this.carregarAlunos(this.recordId()!);
      }
    });
  }

  removerAluno(aluno: any) {
    this.http.delete(`${this.API}/${this.recordId()}/alunos/${aluno.id}`).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Aluno removido da turma' });
        this.carregarAlunos(this.recordId()!);
      }
    });
  }

  onGradeSubmit() {
    if (this.gradeForm.invalid) return;
    const { disciplinaId, professorId, ...horario } = this.gradeForm.value;
    this.http.post(`${this.API}/${this.recordId()}/disciplinas/${disciplinaId}/professor/${professorId}`, horario).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Atribuição adicionada' });
        this.gradeForm.reset({ diaSemana: 'SEGUNDA' });
        this.carregarGrade(this.recordId()!);
      },
      error: (err) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar atribuição' })
    });
  }

  removerAtribuicao(id: string) {
    this.http.delete(`${this.API}/atribuicoes/${id}`).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Atribuição removida' });
        this.carregarGrade(this.recordId()!);
      }
    });
  }
}
