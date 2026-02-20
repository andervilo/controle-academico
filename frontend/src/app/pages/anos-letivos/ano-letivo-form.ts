import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DatePickerModule } from 'primeng/datepicker';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FloatLabelModule } from 'primeng/floatlabel';
import { environment } from '@/environments/environment';

@Component({
  selector: 'app-ano-letivo-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, InputTextModule, InputNumberModule, DatePickerModule, CardModule, ToastModule, FloatLabelModule],
  providers: [MessageService],
  template: `
    <p-toast />
    <div class="flex items-center gap-4 mb-6">
      <p-button icon="pi pi-arrow-left" [rounded]="true" [text]="true" severity="secondary" routerLink="/anos-letivos" />
      <h2 class="m-0 text-color font-bold text-2xl">{{ isEdit() ? 'Editar Ano Letivo' : 'Novo Ano Letivo' }}</h2>
    </div>
    <div class="card">
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <div class="grid grid-cols-12 gap-6">
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on"><p-inputnumber id="ano" formControlName="ano" [useGrouping]="false" [min]="2000" [max]="2099" class="w-full" /><label for="ano">Ano *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on"><p-datepicker id="dataInicio" formControlName="dataInicio" dateFormat="dd/mm/yy" [showIcon]="true" class="w-full" /><label for="dataInicio">Data Início *</label></p-floatlabel>
          </div>
          <div class="col-span-12 md:col-span-4">
            <p-floatlabel variant="on"><p-datepicker id="dataFim" formControlName="dataFim" dateFormat="dd/mm/yy" [showIcon]="true" class="w-full" /><label for="dataFim">Data Fim *</label></p-floatlabel>
          </div>
        </div>
        <div class="flex gap-3 mt-6">
          <p-button [label]="isEdit() ? 'Salvar' : 'Cadastrar'" icon="pi pi-check" type="submit" [loading]="loading()" />
          <p-button label="Cancelar" icon="pi pi-times" severity="secondary" [outlined]="true" routerLink="/anos-letivos" />
        </div>
      </form>
    </div>
  `,
})
export class AnoLetivoFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly messageService = inject(MessageService);
  private readonly API = `${environment.apiUrl}/anos-letivos`;

  form = this.fb.group({ ano: [null as number | null, Validators.required], dataInicio: [null as Date | null, Validators.required], dataFim: [null as Date | null, Validators.required] });
  isEdit = signal(false); loading = signal(false); recordId = signal<string | null>(null);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) { this.isEdit.set(true); this.recordId.set(id); this.http.get<any>(`${this.API}/${id}`).subscribe({ next: (d) => this.form.patchValue({ ...d, dataInicio: d.dataInicio ? new Date(d.dataInicio) : null, dataFim: d.dataFim ? new Date(d.dataFim) : null }) }); }
  }

  onSubmit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading.set(true);
    const data: any = { ...this.form.value };
    if (data.dataInicio instanceof Date) data.dataInicio = data.dataInicio.toISOString().split('T')[0];
    if (data.dataFim instanceof Date) data.dataFim = data.dataFim.toISOString().split('T')[0];
    const req$ = this.isEdit() ? this.http.put(`${this.API}/${this.recordId()}`, data) : this.http.post(this.API, data);
    req$.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: this.isEdit() ? 'Ano letivo atualizado' : 'Ano letivo cadastrado' }); setTimeout(() => this.router.navigate(['/anos-letivos']), 1000); },
      error: (err) => { this.loading.set(false); this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.detail || err.error?.message || 'Erro ao salvar' }); },
    });
  }
}
