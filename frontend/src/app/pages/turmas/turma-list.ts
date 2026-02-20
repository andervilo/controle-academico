import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-turma-list',
  standalone: true,
  imports: [CommonModule, RouterModule, TableModule, ButtonModule, ToastModule, ToolbarModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Turmas</h2></ng-template>
      <ng-template #end><p-button label="Nova Turma" icon="pi pi-plus" severity="primary" routerLink="/turmas/novo" /></ng-template>
    </p-toolbar>
    <div class="card">
      <p-table [value]="items()" [loading]="loading()" dataKey="id">
        <ng-template #header><tr><th>Nome</th><th>Curso</th><th>Ano Letivo</th><th style="width: 10rem">Ações</th></tr></ng-template>
        <ng-template #body let-item>
          <tr>
            <td>{{ item.nome }}</td><td>{{ getCursoNome(item.cursoId) }}</td><td>{{ getAnoNome(item.anoLetivoId) }}</td>
            <td><div class="flex gap-2">
              <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/turmas', item.id, 'editar']" />
              <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
            </div></td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="4" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhuma turma encontrada.</td></tr></ng-template>
      </p-table>
    </div>
  `,
})
export class TurmaListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/turmas`;
  items = signal<any[]>([]); loading = signal(false);
  cursos = signal<any[]>([]); anos = signal<any[]>([]);

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/cursos`).subscribe({ next: (r) => this.cursos.set(r) });
    this.http.get<any[]>(`${environment.apiUrl}/anos-letivos`).subscribe({ next: (r) => this.anos.set(r) });
    this.load();
  }
  load() { this.loading.set(true); this.http.get<any[]>(this.API).subscribe({ next: (r) => { this.items.set(r); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  getCursoNome(id: string) { return this.cursos().find(c => c.id === id)?.nome || id; }
  getAnoNome(id: string) { return this.anos().find(a => a.id === id)?.ano || id; }
  confirmDelete(item: any) { this.confirmationService.confirm({ message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger', accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Turma excluída' }); this.load(); }, error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || e.error?.message || 'Erro ao excluir' }) }) }); }
}
