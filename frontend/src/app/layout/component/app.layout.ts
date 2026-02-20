import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LayoutService } from '@/app/layout/service/layout.service';
import { AppTopbar } from './app.topbar';
import { AppSidebar } from './app.sidebar';

@Component({
    selector: 'app-layout',
    standalone: true,
    imports: [CommonModule, RouterModule, AppTopbar, AppSidebar],
    template: `
    <div class="layout-wrapper" [ngClass]="layoutService.containerClass()">
      <app-sidebar />
      <div class="layout-main-container">
        <app-topbar />
        <div class="layout-main">
          <router-outlet />
        </div>
        <div class="layout-footer">
          <span class="text-muted-color text-sm">Controle Acadêmico &copy; {{ currentYear }}</span>
        </div>
      </div>
    </div>
  `,
})
export class AppLayout {
    layoutService = inject(LayoutService);
    currentYear = new Date().getFullYear();
}
