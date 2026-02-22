import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { TableModule, TableLazyLoadEvent } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { DialogModule } from 'primeng/dialog';
import { TabsModule } from 'primeng/tabs';
import { MessageService, ConfirmationService } from 'primeng/api';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-professor-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, TableModule, ButtonModule, InputTextModule, IconFieldModule, InputIconModule, ToastModule, ToolbarModule, ConfirmDialogModule, DialogModule, TagModule, TabsModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Professores</h2></ng-template>
      <ng-template #end><p-button label="Novo Professor" icon="pi pi-plus" severity="primary" routerLink="/professores/novo" /></ng-template>
    </p-toolbar>
    <div class="card">
      <p-table 
        [value]="items()" 
        [loading]="loading()" 
        [tableStyle]="{ 'min-width': '60rem' }" 
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
        <ng-template #caption>
          <div class="flex items-center justify-between">
            <span class="text-xl text-muted-color">{{ totalRecords() }} registro(s)</span>
            <p-iconfield iconPosition="left"><p-inputicon><i class="pi pi-search"></i></p-inputicon>
              <input pInputText type="text" [(ngModel)]="searchValue" (input)="onSearch()" placeholder="Buscar..." />
            </p-iconfield>
          </div>
        </ng-template>
        <ng-template #header><tr><th>Nome</th><th>CPF</th><th>E-mail</th><th>Telefone</th><th style="width: 10rem">Ações</th></tr></ng-template>
        <ng-template #body let-item>
          <tr>
            <td class="font-bold text-primary">{{ item.nome }}</td><td>{{ item.cpf }}</td><td>{{ item.email }}</td><td>{{ item.telefone }}</td>
            <td>
              <div class="flex gap-2">
                <p-button icon="pi pi-eye" [rounded]="true" [text]="true" severity="secondary" (click)="viewDetails(item)" />
                <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/professores', item.id, 'editar']" />
                <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
              </div>
            </td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="5" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhum professor encontrado.</td></tr></ng-template>
      </p-table>
    </div>

    <!-- Details Dialog -->
    <p-dialog [header]="'Detalhes: ' + selectedProfessor()?.nome" [(visible)]="displayDetails" [modal]="true" [style]="{ width: '50vw' }" [breakpoints]="{ '1199px': '75vw', '575px': '90vw' }">
      @if (detailsLoading()) {
        <div class="flex justify-center p-6"><i class="pi pi-spin pi-spinner text-4xl"></i></div>
      } @else {
        <div class="mt-4">
          <p-tabs value="0">
            <p-tablist>
              <p-tab value="0">Disciplinas</p-tab>
              <p-tab value="1">Disponibilidade</p-tab>
              <p-tab value="2">Turmas</p-tab>
            </p-tablist>
            <p-tabpanels>
              <p-tabpanel value="0">
                <div class="flex flex-wrap gap-2 pt-4">
                  @for (d of professorDisciplinas(); track d.id) {
                    <p-tag [value]="d.nome + ' (' + d.codigo + ')'" severity="info" />
                  } @empty { <p class="text-muted-color">Nenhuma disciplina cadastrada.</p> }
                </div>
              </p-tabpanel>
              <p-tabpanel value="1">
                <p-table [value]="professorDisponibilidades()" class="mt-2">
                  <ng-template #header><tr><th>Dia</th><th>Início</th><th>Fim</th></tr></ng-template>
                  <ng-template #body let-d>
                    <tr>
                      <td>{{ getDiaLabel(d.diaSemana) }}</td><td>{{ d.horaInicio }}</td><td>{{ d.horaFim }}</td>
                    </tr>
                  </ng-template>
                  <ng-template #emptymessage><tr><td colspan="3" class="text-center p-4 text-muted-color">Nenhuma disponibilidade cadastrada.</td></tr></ng-template>
                </p-table>
              </p-tabpanel>
              <p-tabpanel value="2">
                <p-table [value]="professorTurmas()" class="mt-2">
                  <ng-template #header><tr><th>Disciplina</th><th>Turma</th><th>Horário</th></tr></ng-template>
                  <ng-template #body let-t>
                    <tr>
                      <td>{{ t.disciplinaNome }}</td>
                      <td>{{ t.turmaNome }}</td>
                      <td>{{ getDiaLabel(t.diaSemana) }} - {{ t.horaInicio }} às {{ t.horaFim }}</td>
                    </tr>
                  </ng-template>
                  <ng-template #emptymessage><tr><td colspan="3" class="text-center p-4 text-muted-color">Nenhuma turma atribuída.</td></tr></ng-template>
                </p-table>
              </p-tabpanel>
            </p-tabpanels>
          </p-tabs>
        </div>
      }
    </p-dialog>
  `,
})
export class ProfessorListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/professores`;
  items = signal<any[]>([]);
  totalRecords = signal(0);
  rows = signal(10);
  first = signal(0);
  loading = signal(false);
  searchValue = '';

  // Details logic
  displayDetails = false;
  detailsLoading = signal(false);
  selectedProfessor = signal<any>(null);
  professorDisciplinas = signal<any[]>([]);
  professorDisponibilidades = signal<any[]>([]);
  professorTurmas = signal<any[]>([]);

  DIAS_SEMANA = [
    { label: 'Segunda', value: 'SEGUNDA' },
    { label: 'Terça', value: 'TERCA' },
    { label: 'Quarta', value: 'QUARTA' },
    { label: 'Quinta', value: 'QUINTA' },
    { label: 'Sexta', value: 'SEXTA' },
    { label: 'Sábado', value: 'SABADO' },
  ];

  ngOnInit() { /* Defer to onLazyLoad */ }

  load(page: number = 0, size: number = 10) {
    this.loading.set(true);
    const params = { page: page.toString(), size: size.toString() };
    this.http.get<any>(this.API, { params }).subscribe({
      next: (res) => {
        this.items.set(res.content);
        this.totalRecords.set(res.totalElements);
        this.loading.set(false);
      },
      error: (err) => {
        this.loading.set(false);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao carregar' });
      },
    });
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    const page = (event.first || 0) / (event.rows || 10);
    const size = event.rows || 10;
    this.rows.set(size);
    this.first.set(event.first || 0);
    this.load(page, size);
  }

  onSearch() {
    // Server-side search would be better, but keeping it simple for now or disabling if unpaginated
    // For now, let's just log that search should be implemented server-side
    console.log('Search to be implemented server-side');
  }

  viewDetails(professor: any) {
    this.selectedProfessor.set(professor);
    this.displayDetails = true;
    this.detailsLoading.set(true);

    forkJoin({
      disciplinas: this.http.get<any[]>(`${this.API}/${professor.id}/disciplinas`).pipe(catchError(() => of([]))),
      disponibilidades: this.http.get<any[]>(`${this.API}/${professor.id}/disponibilidades`).pipe(catchError(() => of([]))),
      turmas: this.http.get<any[]>(`${this.API}/${professor.id}/turmas-disciplinas`).pipe(catchError(() => of([])))
    }).subscribe({
      next: (res) => {
        this.professorDisciplinas.set(res.disciplinas);
        this.professorDisponibilidades.set(res.disponibilidades);
        this.professorTurmas.set(res.turmas);
        this.detailsLoading.set(false);
      },
      error: (err) => {
        this.detailsLoading.set(false);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao carregar detalhes' });
      }
    });
  }

  getDiaLabel(value: string) { return this.DIAS_SEMANA.find(d => d.value === value)?.label || value; }

  confirmDelete(item: any) {
    this.confirmationService.confirm({
      message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar exclusão', icon: 'pi pi-exclamation-triangle', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Professor excluído' }); this.load(); }, error: (err) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao excluir' }) })
    });
  }
}
