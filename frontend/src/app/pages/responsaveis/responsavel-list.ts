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

@Component({
  selector: 'app-responsavel-list',
  standalone: true,
  imports: [CommonModule, RouterModule, TableModule, ButtonModule, ToastModule, ToolbarModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Responsáveis</h2></ng-template>
      <ng-template #end><p-button label="Novo Responsável" icon="pi pi-plus" severity="primary" routerLink="/responsaveis/novo" /></ng-template>
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
        <ng-template #header><tr><th>Nome</th><th>CPF</th><th>E-mail</th><th>Telefone</th><th style="width: 10rem">Ações</th></tr></ng-template>
        <ng-template #body let-item>
          <tr>
            <td>{{ item.nome }}</td><td>{{ item.cpf }}</td><td>{{ item.email }}</td><td>{{ item.telefone }}</td>
            <td><div class="flex gap-2">
              <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/responsaveis', item.id, 'editar']" />
              <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
            </div></td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="5" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhum responsável encontrado.</td></tr></ng-template>
      </p-table>
    </div>
  `,
})
export class ResponsavelListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/responsaveis`;
  items = signal<any[]>([]);
  loading = signal(false);
  totalRecords = signal(0);
  rows = signal(10);
  first = signal(0);

  ngOnInit() { }
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
  confirmDelete(item: any) { this.confirmationService.confirm({ message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger', accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Responsável excluído' }); this.load(); }, error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || e.error?.message || 'Erro ao excluir' }) }) }); }
}
