import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { SelectModule } from 'primeng/select';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { MessageService, ConfirmationService } from 'primeng/api';
import { environment } from '@/environments/environment';

const DIAS_SEMANA = [
  { label: 'Segunda', value: 'SEGUNDA' },
  { label: 'Terça', value: 'TERCA' },
  { label: 'Quarta', value: 'QUARTA' },
  { label: 'Quinta', value: 'QUINTA' },
  { label: 'Sexta', value: 'SEXTA' },
  { label: 'Sábado', value: 'SABADO' },
];

@Component({
  selector: 'app-disponibilidade',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ButtonModule, SelectModule, CardModule, TableModule, ToastModule, ToolbarModule, FloatLabelModule, InputTextModule, ConfirmDialogModule, TagModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Disponibilidade de Professores</h2></ng-template>
    </p-toolbar>

    <div class="grid grid-cols-12 gap-6">
      <!-- Selecionar professor -->
      <div class="col-span-12">
        <div class="card">
          <h3 class="text-lg font-semibold mb-4 text-color">Selecionar Professor</h3>
          <p-select
            [options]="professores()"
            optionLabel="nome"
            optionValue="id"
            [(ngModel)]="selectedProfessorId"
            (onChange)="onProfessorChange()"
            placeholder="Selecione um professor..."
            [filter]="true"
            filterBy="nome"
            class="w-full md:w-1/2"
          />
        </div>
      </div>

      @if (selectedProfessorId) {
        <!-- Add new slot -->
        <div class="col-span-12 lg:col-span-5">
          <div class="card">
            <h3 class="text-lg font-semibold mb-4 text-color"><i class="pi pi-plus-circle mr-2 text-primary"></i>Adicionar Horário</h3>
            <form [formGroup]="form" (ngSubmit)="onSubmit()">
              <div class="flex flex-col gap-4">
                <p-floatlabel variant="on">
                  <p-select id="diaSemana" formControlName="diaSemana" [options]="diasSemana" optionLabel="label" optionValue="value" class="w-full" />
                  <label for="diaSemana">Dia da Semana *</label>
                </p-floatlabel>
                <div class="grid grid-cols-2 gap-4">
                  <p-floatlabel variant="on">
                    <input pInputText id="horaInicio" formControlName="horaInicio" type="time" class="w-full" />
                    <label for="horaInicio">Início *</label>
                  </p-floatlabel>
                  <p-floatlabel variant="on">
                    <input pInputText id="horaFim" formControlName="horaFim" type="time" class="w-full" />
                    <label for="horaFim">Fim *</label>
                  </p-floatlabel>
                </div>
                <p-button label="Adicionar" icon="pi pi-plus" type="submit" [loading]="loading()" class="w-full" />
              </div>
            </form>
          </div>
        </div>

        <!-- Current slots -->
        <div class="col-span-12 lg:col-span-7">
          <div class="card">
            <h3 class="text-lg font-semibold mb-4 text-color"><i class="pi pi-clock mr-2 text-primary"></i>Horários Cadastrados</h3>
            <p-table [value]="disponibilidades()" dataKey="id">
              <ng-template #header><tr><th>Dia</th><th>Início</th><th>Fim</th><th style="width: 5rem"></th></tr></ng-template>
              <ng-template #body let-item>
                <tr>
                  <td><p-tag [value]="getDiaLabel(item.diaSemana)" severity="info" /></td>
                  <td>{{ item.horaInicio }}</td><td>{{ item.horaFim }}</td>
                  <td><p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" /></td>
                </tr>
              </ng-template>
              <ng-template #emptymessage><tr><td colspan="4" class="text-center p-4 text-muted-color">Nenhum horário cadastrado.</td></tr></ng-template>
            </p-table>
          </div>
        </div>
      }
    </div>
  `,
})
export class DisponibilidadeComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly fb = inject(FormBuilder);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);

  professores = signal<any[]>([]);
  disponibilidades = signal<any[]>([]);
  selectedProfessorId: string | null = null;
  loading = signal(false);
  diasSemana = DIAS_SEMANA;

  form = this.fb.group({
    diaSemana: ['', Validators.required],
    horaInicio: ['', Validators.required],
    horaFim: ['', Validators.required],
  });

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/professores/todos`).subscribe({ next: (r) => this.professores.set(r) });
  }

  onProfessorChange() {
    if (this.selectedProfessorId) this.loadDisponibilidades();
  }

  loadDisponibilidades() {
    this.http.get<any[]>(`${environment.apiUrl}/professores/${this.selectedProfessorId}/disponibilidades`).subscribe({
      next: (r) => this.disponibilidades.set(r),
    });
  }

  getDiaLabel(value: string) { return DIAS_SEMANA.find(d => d.value === value)?.label || value; }

  onSubmit() {
    if (this.form.invalid || !this.selectedProfessorId) return;
    this.loading.set(true);
    this.http.post(`${environment.apiUrl}/professores/${this.selectedProfessorId}/disponibilidades`, this.form.value).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Disponibilidade adicionada' }); this.form.reset(); this.loading.set(false); this.loadDisponibilidades(); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao adicionar' }); },
    });
  }

  confirmDelete(item: any) {
    this.confirmationService.confirm({
      message: `Remover disponibilidade de ${this.getDiaLabel(item.diaSemana)} ${item.horaInicio}-${item.horaFim}?`,
      header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.http.delete(`${environment.apiUrl}/professores/disponibilidades/${item.id}`).subscribe({
        next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Disponibilidade removida' }); this.loadDisponibilidades(); },
      }),
    });
  }
}
