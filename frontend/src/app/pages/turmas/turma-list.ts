import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { TableModule, TableLazyLoadEvent } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { environment } from '@/environments/environment';
import { DialogModule } from 'primeng/dialog';
import { TabsModule } from 'primeng/tabs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-turma-list',
  standalone: true,
  imports: [CommonModule, RouterModule, TableModule, ButtonModule, ToastModule, ToolbarModule, ConfirmDialogModule, DialogModule, TabsModule, FormsModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Turmas</h2></ng-template>
      <ng-template #end><p-button label="Nova Turma" icon="pi pi-plus" severity="primary" routerLink="/turmas/novo" /></ng-template>
    </p-toolbar>
    <div class="card">
      <p-table 
        [value]="items()" 
        [loading]="loading()" 
        dataKey="id"
        [lazy]="true"
        (onLazyLoad)="onLazyLoad($event)"
        [rows]="rows()"
        [totalRecords]="totalRecords()"
        [paginator]="true"
        [alwaysShowPaginator]="false"
        [first]="first()"
        [rowsPerPageOptions]="[10, 15, 20, 50, 100]"
      >
        <ng-template #header><tr><th>Nome</th><th>Curso</th><th>Ano Letivo</th><th style="width: 10rem">Ações</th></tr></ng-template>
        <ng-template #body let-item>
          <tr>
            <td>{{ item.nome }}</td><td>{{ getCursoNome(item.cursoId) }}</td><td>{{ getAnoNome(item.anoLetivoId) }}</td>
            <td><div class="flex gap-2">
              <p-button icon="pi pi-eye" [rounded]="true" [text]="true" severity="info" pTooltip="Visualizar Turma" (onClick)="viewTurma(item)" />
              <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" [routerLink]="['/turmas', item.id, 'editar']" />
              <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (onClick)="confirmDelete(item)" />
            </div></td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="4" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhuma turma encontrada.</td></tr></ng-template>
      </p-table>
    </div>

    <!-- Viewer Context -->
    <p-dialog [(visible)]="viewDialog" [style]="{ width: '70vw' }" [header]="'Turma: ' + (selectedTurma()?.nome || '')" [modal]="true" styleClass="p-fluid">
      <p-tabs value="0">
        <p-tablist>
            <p-tab value="0"><i class="pi pi-book mr-2"></i>Disciplinas & Professores</p-tab>
            <p-tab value="1"><i class="pi pi-users mr-2"></i>Alunos Matriculados</p-tab>
        </p-tablist>
        <p-tabpanels>
            <p-tabpanel value="0">
              <p-table [value]="disciplinasProfessores()" responsiveLayout="scroll">
                <ng-template #header>
                  <tr>
                    <th>Disciplina</th>
                    <th>Professor</th>
                    <th>Dia da Semana</th>
                    <th>Início</th>
                    <th>Fim</th>
                  </tr>
                </ng-template>
                <ng-template #body let-dp>
                  <tr>
                    <td>{{ dp.disciplinaNome }}</td>
                    <td>{{ dp.professorNome }}</td>
                    <td>{{ dp.diaSemana }}</td>
                    <td>{{ dp.horaInicio }}</td>
                    <td>{{ dp.horaFim }}</td>
                  </tr>
                </ng-template>
                <ng-template #emptymessage><tr><td colspan="5" class="text-center p-4">Nenhuma disciplina atribuída</td></tr></ng-template>
              </p-table>
            </p-tabpanel>
            
            <p-tabpanel value="1">
              <p-toolbar styleClass="mb-4">
                <ng-template #end><p-button label="Matricular Alunos" icon="pi pi-user-plus" severity="primary" size="small" (onClick)="openMatriculaDialog()" /></ng-template>
              </p-toolbar>
              <p-table [value]="alunosMatriculados()" [loading]="loadingAlunos()" responsiveLayout="scroll">
                <ng-template #header><tr><th>Matrícula</th><th>Nome</th><th>Ações</th></tr></ng-template>
                <ng-template #body let-al>
                  <tr>
                    <td>{{ al.matricula }}</td><td>{{ al.nome }}</td>
                    <td><p-button icon="pi pi-user-minus" [rounded]="true" [text]="true" severity="danger" pTooltip="Desvincular" (onClick)="desvincularAluno(al.id)" /></td>
                  </tr>
                </ng-template>
                <ng-template #emptymessage><tr><td colspan="3" class="text-center p-4">Nenhum aluno matriculado nesta turma</td></tr></ng-template>
              </p-table>
            </p-tabpanel>
        </p-tabpanels>
      </p-tabs>
    </p-dialog>

    <p-dialog [(visible)]="matriculaDialog" [style]="{ width: '60vw' }" header="Selecionar Alunos para Matrícula" [modal]="true" styleClass="p-fluid">
      <p-table [value]="alunosDisponiveis()" [(selection)]="selectedAlunos" dataKey="id" [loading]="loadingDisponiveis()">
        <ng-template #header>
          <tr>
            <th style="width: 4rem"><p-tableHeaderCheckbox /></th>
            <th>Matrícula</th>
            <th>Nome</th>
          </tr>
        </ng-template>
        <ng-template #body let-al>
          <tr>
            <td><p-tableCheckbox [value]="al" /></td>
            <td>{{ al.matricula }}</td>
            <td>{{ al.nome }}</td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="3" class="text-center p-4">Não há alunos disponíveis para matricular</td></tr></ng-template>
      </p-table>
      <ng-template #footer>
        <p-button label="Cancelar" icon="pi pi-times" [text]="true" severity="secondary" (onClick)="matriculaDialog = false" />
        <p-button label="Confirmar Matrícula" icon="pi pi-check" (onClick)="confirmarMatricula()" [disabled]="selectedAlunos?.length === 0" />
      </ng-template>
    </p-dialog>
  `,
})
export class TurmaListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/turmas`;
  items = signal<any[]>([]);
  loading = signal(false);
  totalRecords = signal(0);
  rows = signal(10);
  first = signal(0);

  cursos = signal<any[]>([]); anos = signal<any[]>([]);

  // View Modal State
  viewDialog = false;
  selectedTurma = signal<any>(null);
  disciplinasProfessores = signal<any[]>([]);
  alunosMatriculados = signal<any[]>([]);
  loadingAlunos = signal(false);

  // Matricula Modal State
  matriculaDialog = false;
  alunosDisponiveis = signal<any[]>([]);
  loadingDisponiveis = signal(false);
  selectedAlunos: any[] = [];

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/cursos/todos`).subscribe({
      next: (r) => {
        // If server returns paginated, adjust here
        this.cursos.set(r);
      }
    });
    this.http.get<any[]>(`${environment.apiUrl}/anos-letivos/todos`).subscribe({
      next: (r) => {
        // If server returns paginated, adjust here
        this.anos.set(r);
      }
    });
  }
  load(page: number = 0, size: number = 10) {
    this.loading.set(true);
    const params = { page: page.toString(), size: size.toString() };
    this.http.get<any>(this.API, { params }).subscribe({
      next: (r) => {
        this.items.set(r.content);
        this.totalRecords.set(r.totalElements);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    const page = (event.first || 0) / (event.rows || 10);
    const size = event.rows || 10;
    this.rows.set(size);
    this.first.set(event.first || 0);
    this.load(page, size);
  }
  getCursoNome(id: string) { return this.cursos().find(c => c.id === id)?.nome || id; }
  getAnoNome(id: string) { return this.anos().find(a => a.id === id)?.ano || id; }
  confirmDelete(item: any) { this.confirmationService.confirm({ message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger', accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Turma excluída' }); this.load(); }, error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || e.error?.message || 'Erro ao excluir' }) }) }); }

  viewTurma(item: any) {
    this.selectedTurma.set(item);
    this.viewDialog = true;
    this.loadViewDashboard();
  }

  loadViewDashboard() {
    const tid = this.selectedTurma()?.id;
    if (!tid) return;
    this.loadingAlunos.set(true);

    // Load Alunos
    this.http.get<any[]>(`${this.API}/${tid}/alunos`).subscribe({
      next: (r) => { this.alunosMatriculados.set(r); this.loadingAlunos.set(false); },
      error: () => this.loadingAlunos.set(false)
    });

    // Load Disciplinas/Professores
    this.http.get<any[]>(`${this.API}/${tid}/disciplinas-professores`).subscribe({
      next: (r) => this.disciplinasProfessores.set(r)
    });
  }

  openMatriculaDialog() {
    const tid = this.selectedTurma()?.id;
    if (!tid) return;
    this.selectedAlunos = [];
    this.matriculaDialog = true;
    this.loadingDisponiveis.set(true);

    this.http.get<any[]>(`${this.API}/${tid}/alunos-disponiveis`).subscribe({
      next: (r) => { this.alunosDisponiveis.set(r); this.loadingDisponiveis.set(false); },
      error: () => this.loadingDisponiveis.set(false)
    });
  }

  confirmarMatricula() {
    const tid = this.selectedTurma()?.id;
    if (!tid || !this.selectedAlunos.length) return;
    const body = { alunoIds: this.selectedAlunos.map(a => a.id) };

    this.http.post(`${this.API}/${tid}/alunos/lote`, body).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Alunos matriculados com sucesso' });
        this.matriculaDialog = false;
        this.loadViewDashboard(); // Reload tab 2
      },
      error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || 'Erro ao matricular' })
    });
  }

  desvincularAluno(alunoId: string) {
    this.confirmationService.confirm({
      message: 'Deseja remover este aluno da turma?', header: 'Confirmar Desvinculação', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.http.delete(`${this.API}/${this.selectedTurma().id}/alunos/${alunoId}`).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Aluno desvinculado' });
            this.loadViewDashboard();
          }
        });
      }
    });
  }

}
