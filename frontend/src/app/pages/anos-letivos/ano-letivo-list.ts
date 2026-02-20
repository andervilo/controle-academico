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
  selector: 'app-ano-letivo-list',
  standalone: true,
  imports: [CommonModule, RouterModule, TableModule, ButtonModule, ToastModule, ToolbarModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Anos Letivos</h2></ng-template>
      <ng-template #end><p-button label="Novo Ano Letivo" icon="pi pi-plus" severity="primary" routerLink="/anos-letivos/novo" /></ng-template>
    </p-toolbar>
    <div class="card">
      <p-table [value]="items()" [loading]="loading()" dataKey="id">
        <ng-template #header><tr><th>Ano</th><th>Data Início</th><th>Data Fim</th><th style="width: 10rem">Ações</th></tr></ng-template>
               <ng-template #body let-item>
          <tr>
            <td>{{ item.ano }}</td><td>{{ item.dataInicio }}</td><td>{{ item.dataFim }}</td>
            <td><div class="flex gap-2">
              <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/anos-letivos', item.id, 'editar']" />
              <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
            </div></td>
          </tr>
        </ng-template>
        <ng-template #emptymessage><tr><td colspan="4" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhum ano letivo encontrado.</td></tr></ng-template>
      </p-table>
    </div>
  `,
})
export class AnoLetivoListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/anos-letivos`;
  items = signal<any[]>([]); loading = signal(false);
  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.http.get<any[]>(this.API).subscribe({ next: (r) => { this.items.set(r); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  confirmDelete(item: any) { this.confirmationService.confirm({ message: `Deseja excluir "${item.ano}"?`, header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger', accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({ next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Ano letivo excluído' }); this.load(); }, error: (e) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: e.error?.detail || e.error?.message || 'Erro ao excluir' }) }) }); }
}
