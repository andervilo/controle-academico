import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-disciplina-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, TableModule, ButtonModule, InputTextModule, IconFieldModule, InputIconModule, ToastModule, ToolbarModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Disciplinas</h2></ng-template>
      <ng-template #end><p-button label="Nova Disciplina" icon="pi pi-plus" severity="primary" routerLink="/disciplinas/novo" /></ng-template>
    </p-toolbar>
    <div class="card">
      <p-table [value]="items()" [loading]="loading()" [tableStyle]="{ 'min-width': '40rem' }" dataKey="id">
        <ng-template #caption>
          <div class="flex items-center justify-between">
            <span class="text-xl text-muted-color">{{ items().length }} registro(s)</span>
            <p-iconfield iconPosition="left"><p-inputicon><i class="pi pi-search"></i></p-inputicon>
              <input pInputText type="text" [(ngModel)]="searchValue" (input)="onSearch()" placeholder="Buscar..." />
            </p-iconfield>
          </div>
        </ng-template>
        <ng-template #header><tr><th>Nome</th><th>Carga Horária</th><th style="width: 10rem">Ações</th></tr></ng-template>
        <ng-template #body let-item>
          <tr>
            <td>{{ item.nome }}</td><td>{{ item.cargaHoraria }}h</td>
            <td>
              <div class="flex gap-2">
                <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/disciplinas', item.id, 'editar']" />
                <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
              </div>
            </td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="3" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhuma disciplina encontrada.</td></tr></ng-template>
      </p-table>
    </div>
  `,
})
export class DisciplinaListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/disciplinas`;
  items = signal<any[]>([]); allItems = signal<any[]>([]); loading = signal(false); searchValue = '';

  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.http.get<any[]>(this.API).subscribe({ next: (r) => { this.allItems.set(r); this.items.set(r); this.loading.set(false); }, error: () => { this.loading.set(false); } }); }
  onSearch() { const s = this.searchValue.toLowerCase(); this.items.set(this.allItems().filter(i => i.nome.toLowerCase().includes(s))); }
  confirmDelete(item: any) { this.confirmationService.confirm({ message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar', icon: 'pi pi-exclamation-triangle', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger', accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Disciplina excluída' }); this.load(); }, error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || e.error?.message || 'Erro ao excluir' }) }) }); }
}
