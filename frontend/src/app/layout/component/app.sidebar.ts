import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LayoutService } from '@/app/layout/service/layout.service';

interface MenuItem {
  label?: string;
  icon?: string;
  routerLink?: string[];
  separator?: boolean;
  items?: MenuItem[];
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="layout-sidebar bg-surface-0 dark:bg-surface-900 border-r border-surface">
      <div class="p-4 mb-2">
        <div class="flex items-center gap-3">
          <i class="pi pi-graduation-cap text-primary text-3xl"></i>
          <div>
            <div class="font-bold text-lg text-color">SDD</div>
            <div class="text-xs text-muted-color">Controle Acadêmico</div>
          </div>
        </div>
      </div>

      <ul class="layout-menu px-3">
        @for (group of menu; track group.label) {
          @if (group.separator) {
            <li class="menu-separator"></li>
          }
          @if (group.label && group.items) {
            <li>
              <span class="layout-menuitem-root-text text-muted-color">{{ group.label }}</span>
              <ul class="layout-menu">
                @for (item of group.items; track item.label) {
                  <li>
                    <a
                      [routerLink]="item.routerLink"
                      routerLinkActive="active-route"
                      [routerLinkActiveOptions]="{ exact: item.routerLink?.[0] === '/dashboard' }"
                      (click)="layoutService.onMenuItemClick()"
                      class="text-color"
                    >
                      <i [class]="item.icon" class="text-lg"></i>
                      <span>{{ item.label }}</span>
                    </a>
                  </li>
                }
              </ul>
            </li>
          }
        }
      </ul>
    </div>
  `,
})
export class AppSidebar {
  layoutService = inject(LayoutService);

  menu: MenuItem[] = [
    {
      label: 'Principal',
      items: [
        { label: 'Dashboard', icon: 'pi pi-home', routerLink: ['/dashboard'] },
      ],
    },
    {
      label: 'Cadastros',
      items: [
        { label: 'Alunos', icon: 'pi pi-users', routerLink: ['/alunos'] },
        { label: 'Professores', icon: 'pi pi-user', routerLink: ['/professores'] },
        { label: 'Disciplinas', icon: 'pi pi-book', routerLink: ['/disciplinas'] },
        { label: 'Cursos', icon: 'pi pi-briefcase', routerLink: ['/cursos'] },
        { label: 'Responsáveis', icon: 'pi pi-id-card', routerLink: ['/responsaveis'] },
      ],
    },
    {
      label: 'Acadêmico',
      items: [
        { label: 'Ano Letivo', icon: 'pi pi-calendar', routerLink: ['/anos-letivos'] },
        { label: 'Turmas', icon: 'pi pi-sitemap', routerLink: ['/turmas'] },
        { label: 'Grade Horária', icon: 'pi pi-clock', routerLink: ['/grade-horaria'] },
        { label: 'Disponibilidade', icon: 'pi pi-check-circle', routerLink: ['/disponibilidade'] },
      ],
    },
    {
      label: 'Administrativo',
      items: [
        { label: 'Configurações', icon: 'pi pi-cog', routerLink: ['/configuracoes'] },
      ],
    },
  ];
}
