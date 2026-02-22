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
import { MessageService, ConfirmationService } from 'primeng/api';
import { environment } from '@/environments/environment';

interface Aluno {
  id: string;
  nome: string;
  matricula: string;
  cpf: string;
  dataNascimento: string;
  email: string;
  telefone: string;
}

@Component({
  selector: 'app-aluno-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, TableModule, ButtonModule, InputTextModule, IconFieldModule, InputIconModule, ToastModule, ToolbarModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast />
    <p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start>
        <h2 class="m-0 text-color font-bold text-2xl">Alunos</h2>
      </ng-template>
      <ng-template #end>
        <p-button label="Novo Aluno" icon="pi pi-plus" severity="primary" routerLink="/alunos/novo" />
      </ng-template>
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
            <p-iconfield iconPosition="left">
              <p-inputicon><i class="pi pi-search"></i></p-inputicon>
              <input pInputText type="text" [(ngModel)]="searchValue" (input)="onSearch()" placeholder="Buscar..." />
            </p-iconfield>
          </div>
        </ng-template>
        <ng-template #header>
          <tr>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>E-mail</th>
            <th>Telefone</th>
            <th style="width: 10rem">Ações</th>
          </tr>
        </ng-template>
        <ng-template #body let-item>
          <tr>
            <td class="font-bold text-primary">{{ item.matricula }}</td>
            <td>{{ item.nome }}</td>
            <td>{{ item.cpf }}</td>
            <td>{{ item.email }}</td>
            <td>{{ item.telefone }}</td>
            <td>
              <div class="flex gap-2">
                <p-button icon="pi pi-pencil" [rounded]="true" [text]="true" severity="info" [routerLink]="['/alunos', item.id, 'editar']" />
                <p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" />
              </div>
            </td>
          </tr>
        </ng-template>
        <ng-template #emptymessage>
          <tr><td colspan="6" class="text-center p-6 text-muted-color"><i class="pi pi-inbox text-4xl mb-4 block"></i>Nenhum aluno encontrado.</td></tr>
        </ng-template>
      </p-table>
    </div>
  `,
})
export class AlunoListComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly API = `${environment.apiUrl}/alunos`;

  items = signal<Aluno[]>([]);
  totalRecords = signal(0);
  rows = signal(10);
  first = signal(0);
  loading = signal(false);
  searchValue = '';

  ngOnInit() { }

  load(page: number = 0, size: number = 10) {
    this.loading.set(true);
    const params = { page: page.toString(), size: size.toString() };
    this.http.get<any>(this.API, { params }).subscribe({
      next: (res) => {
        this.items.set(res.content);
        this.totalRecords.set(res.totalElements);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao carregar alunos' });
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
    console.log('Search to be implemented server-side');
  }

  confirmDelete(item: Aluno) {
    this.confirmationService.confirm({
      message: `Deseja excluir "${item.nome}"?`, header: 'Confirmar exclusão', icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.http.delete(`${this.API}/${item.id}`).subscribe({
        next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Aluno excluído' }); this.load(); },
        error: (err) => this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao excluir' }),
      }),
    });
  }
}
