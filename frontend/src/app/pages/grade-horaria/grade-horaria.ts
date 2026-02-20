import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormsModule } from '@angular/forms';
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
import { DialogModule } from 'primeng/dialog';
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
  selector: 'app-grade-horaria',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ButtonModule, SelectModule, CardModule, TableModule, ToastModule, ToolbarModule, FloatLabelModule, InputTextModule, ConfirmDialogModule, TagModule, DialogModule],
  providers: [MessageService, ConfirmationService],
  template: `
    <p-toast /><p-confirmdialog />
    <p-toolbar styleClass="mb-6 gap-2">
      <ng-template #start><h2 class="m-0 text-color font-bold text-2xl">Grade Horária</h2></ng-template>
    </p-toolbar>

    <div class="grid grid-cols-12 gap-6">
      <!-- Select turma -->
      <div class="col-span-12">
        <div class="card">
          <h3 class="text-lg font-semibold mb-4 text-color">Selecionar Turma</h3>
          <p-select
            [options]="turmas()"
            optionLabel="nome"
            optionValue="id"
            [(ngModel)]="selectedTurmaId"
            (onChange)="onTurmaChange()"
            placeholder="Selecione uma turma..."
            [filter]="true"
            filterBy="nome"
            class="w-full md:w-1/2"
          />
        </div>
      </div>

      @if (selectedTurmaId) {
        <!-- Current assignments -->
        <div class="col-span-12">
          <div class="card">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-semibold text-color m-0"><i class="pi pi-calendar mr-2 text-primary"></i>Aulas Atribuídas</h3>
              <p-button label="Nova Atribuição" icon="pi pi-plus" size="small" (click)="showDialog = true" />
            </div>
            <p-table [value]="atribuicoes()" dataKey="id">
              <ng-template #header><tr><th>Disciplina</th><th>Professor</th><th>Dia</th><th>Início</th><th>Fim</th><th style="width: 5rem"></th></tr></ng-template>
              <ng-template #body let-item>
                <tr>
                  <td>{{ item.disciplinaNome }}</td><td>{{ item.professorNome }}</td>
                  <td><p-tag [value]="getDiaLabel(item.diaSemana)" severity="info" /></td>
                  <td>{{ item.horaInicio }}</td><td>{{ item.horaFim }}</td>
                  <td><p-button icon="pi pi-trash" [rounded]="true" [text]="true" severity="danger" (click)="confirmDelete(item)" /></td>
                </tr>
              </ng-template>
              <ng-template #emptymessage><tr><td colspan="6" class="text-center p-4 text-muted-color">Nenhuma aula atribuída.</td></tr></ng-template>
            </p-table>
          </div>
        </div>
      }
    </div>

    <!-- Dialog for new assignment -->
    <p-dialog header="Nova Atribuição" [(visible)]="showDialog" [modal]="true" [style]="{ width: '30rem' }">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="flex flex-col gap-4 mt-4">
          <p-floatlabel variant="on">
            <p-select id="disciplinaId" formControlName="disciplinaId" [options]="disciplinas()" optionLabel="nome" optionValue="id" [filter]="true" class="w-full" />
            <label for="disciplinaId">Disciplina *</label>
          </p-floatlabel>
          <p-floatlabel variant="on">
            <p-select id="professorId" formControlName="professorId" [options]="professores()" optionLabel="nome" optionValue="id" [filter]="true" class="w-full" />
            <label for="professorId">Professor *</label>
          </p-floatlabel>
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
          <div class="flex gap-3 justify-end mt-2">
            <p-button label="Cancelar" severity="secondary" [outlined]="true" (click)="showDialog = false" />
            <p-button label="Atribuir" icon="pi pi-check" type="submit" [loading]="loading()" />
          </div>
        </div>
      </form>
    </p-dialog>
  `,
})
export class GradeHorariaComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly fb = inject(FormBuilder);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);

  turmas = signal<any[]>([]);
  disciplinas = signal<any[]>([]);
  professores = signal<any[]>([]);
  atribuicoes = signal<any[]>([]);
  selectedTurmaId: string | null = null;
  showDialog = false;
  loading = signal(false);
  diasSemana = DIAS_SEMANA;

  form = this.fb.group({
    disciplinaId: ['', Validators.required],
    professorId: ['', Validators.required],
    diaSemana: ['', Validators.required],
    horaInicio: ['', Validators.required],
    horaFim: ['', Validators.required],
  });

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/turmas`).subscribe({ next: (r) => this.turmas.set(r) });
    this.http.get<any[]>(`${environment.apiUrl}/disciplinas`).subscribe({ next: (r) => this.disciplinas.set(r) });
    this.http.get<any[]>(`${environment.apiUrl}/professores`).subscribe({ next: (r) => this.professores.set(r) });
  }

  onTurmaChange() { if (this.selectedTurmaId) this.loadAtribuicoes(); }

  loadAtribuicoes() {
    this.http.get<any[]>(`${environment.apiUrl}/turmas/${this.selectedTurmaId}/disciplinas-professores`).subscribe({
      next: (r) => this.atribuicoes.set(r),
    });
  }

  getDiaLabel(value: string) { return DIAS_SEMANA.find(d => d.value === value)?.label || value; }

  onSubmit() {
    if (this.form.invalid || !this.selectedTurmaId) return;
    this.loading.set(true);
    const data = this.form.value;
    this.http.post(
      `${environment.apiUrl}/turmas/${this.selectedTurmaId}/disciplinas/${data.disciplinaId}/professor/${data.professorId}`,
      { diaSemana: data.diaSemana, horaInicio: data.horaInicio, horaFim: data.horaFim }
    ).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Atribuição criada' }); this.showDialog = false; this.form.reset(); this.loading.set(false); this.loadAtribuicoes(); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao atribuir' }); },
    });
  }

  confirmDelete(item: any) {
    this.confirmationService.confirm({
      message: `Remover atribuição de ${item.disciplinaNome} - ${item.professorNome}?`,
      header: 'Confirmar', acceptLabel: 'Sim', rejectLabel: 'Cancelar', acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.http.delete(`${environment.apiUrl}/turmas/atribuicoes/${item.id}`).subscribe({
        next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Atribuição removida' }); this.loadAtribuicoes(); },
      }),
    });
  }
}
