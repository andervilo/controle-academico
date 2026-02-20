import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { LayoutService } from '@/app/layout/service/layout.service';

@Component({
    selector: 'app-topbar',
    standalone: true,
    imports: [CommonModule, ButtonModule],
    template: `
    <div class="layout-topbar bg-surface-0 dark:bg-surface-900 border-b border-surface">
      <div class="flex items-center gap-3">
        <p-button
          icon="pi pi-bars"
          [text]="true"
          [rounded]="true"
          severity="secondary"
          (click)="layoutService.toggleMenu()"
        />
        <span class="text-xl font-bold text-primary">
          <i class="pi pi-graduation-cap mr-2"></i>Controle Acadêmico
        </span>
      </div>
      <div class="flex items-center gap-2">
        <p-button
          [icon]="layoutService.isDarkTheme() ? 'pi pi-sun' : 'pi pi-moon'"
          [text]="true"
          [rounded]="true"
          severity="secondary"
          (click)="layoutService.toggleDarkMode()"
        />
      </div>
    </div>
  `,
})
export class AppTopbar {
    layoutService = inject(LayoutService);
}
