import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CardModule } from 'primeng/card';
import { environment } from '@/environments/environment';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule, CardModule],
    template: `
    <div class="grid grid-cols-12 gap-6">
      <!-- Stats -->
      <div class="col-span-12 md:col-span-6 xl:col-span-3">
        <p-card>
          <div class="flex items-center justify-between">
            <div>
              <div class="text-muted-color font-semibold text-sm">Alunos</div>
              <div class="text-4xl font-bold mt-2">{{ stats().alunos }}</div>
            </div>
            <i class="pi pi-users text-primary text-4xl"></i>
          </div>
        </p-card>
      </div>
      <div class="col-span-12 md:col-span-6 xl:col-span-3">
        <p-card>
          <div class="flex items-center justify-between">
            <div>
              <div class="text-muted-color font-semibold text-sm">Professores</div>
              <div class="text-4xl font-bold mt-2">{{ stats().professores }}</div>
            </div>
            <i class="pi pi-user text-cyan-500 text-4xl"></i>
          </div>
        </p-card>
      </div>
      <div class="col-span-12 md:col-span-6 xl:col-span-3">
        <p-card>
          <div class="flex items-center justify-between">
            <div>
              <div class="text-muted-color font-semibold text-sm">Disciplinas</div>
              <div class="text-4xl font-bold mt-2">{{ stats().disciplinas }}</div>
            </div>
            <i class="pi pi-book text-orange-500 text-4xl"></i>
          </div>
        </p-card>
      </div>
      <div class="col-span-12 md:col-span-6 xl:col-span-3">
        <p-card>
          <div class="flex items-center justify-between">
            <div>
              <div class="text-muted-color font-semibold text-sm">Turmas</div>
              <div class="text-4xl font-bold mt-2">{{ stats().turmas }}</div>
            </div>
            <i class="pi pi-sitemap text-purple-500 text-4xl"></i>
          </div>
        </p-card>
      </div>

      <!-- Welcome -->
      <div class="col-span-12">
        <p-card>
          <div class="text-center py-8">
            <i class="pi pi-graduation-cap text-primary text-6xl mb-4 block"></i>
            <h2 class="text-3xl font-bold mb-2 text-color">Sistema de Controle Acadêmico</h2>
            <p class="text-muted-color text-lg">
              Gerencie alunos, professores, disciplinas, turmas e a grade horária da sua instituição.
            </p>
          </div>
        </p-card>
      </div>
    </div>
  `,
})
export class Dashboard implements OnInit {
    private readonly http = inject(HttpClient);
    private readonly API = environment.apiUrl;

    stats = signal({ alunos: 0, professores: 0, disciplinas: 0, turmas: 0 });

    ngOnInit() {
        this.loadCount('alunos', 'alunos');
        this.loadCount('professores', 'professores');
        this.loadCount('disciplinas', 'disciplinas');
        this.loadCount('turmas', 'turmas');
    }

    private loadCount(endpoint: string, key: keyof ReturnType<typeof this.stats>) {
        this.http.get<any[]>(`${this.API}/${endpoint}`).subscribe({
            next: (res) => this.stats.update((s) => ({ ...s, [key]: Array.isArray(res) ? res.length : 0 })),
            error: () => { },
        });
    }
}
