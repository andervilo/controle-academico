---
name: primeng-sakai-design-system   
description: Esta skill documenta a identidade visual completa, padrões de design e arquitetura do template **Sakai-ng** para projetos PrimeNG + Angular. Use esta skill para criar ou refatorar projetos frontend mantendo consistência visual e boas práticas.
---

# PrimeNG Sakai Design System

Esta skill documenta a identidade visual completa, padrões de design e arquitetura do template **Sakai-ng** para projetos PrimeNG + Angular. Use esta skill para criar ou refatorar projetos frontend mantendo consistência visual e boas práticas.

## 📋 Visão Geral

**Sakai** é um template administrativo profissional desenvolvido pela PrimeTek para demonstrar as capacidades do PrimeNG v21. É um sistema de design completo que combina:

- **Framework**: Angular 21+ (zoneless)
- **UI Library**: PrimeNG 21+  
- **Styling**: TailwindCSS 4+ com `tailwindcss-primeui`
- **Theming**: @primeuix/themes v2 (Aura, Lara, Nora presets)
- **Icons**: PrimeIcons 7+
- **Arquitetura**: Standalone Components + Signals

## 🎨 Sistema de Theming

### Configuração Base

```typescript
// app.config.ts
import Aura from '@primeuix/themes/aura';
import { providePrimeNG } from 'primeng/config';

export const appConfig: ApplicationConfig = {
    providers: [
        providePrimeNG({ 
            theme: { 
                preset: Aura, 
                options: { 
                    darkModeSelector: '.app-dark' 
                } 
            } 
        })
    ]
};
```

### Presets Disponíveis

O Sakai suporta 3 presets oficiais:

1. **Aura** (padrão) - Design system moderno e vibrante
2. **Lara** - Design system equilibrado e profissional  
3. **Nora** - Design system ousado e contemporâneo

```typescript
import Aura from '@primeuix/themes/aura';
import Lara from '@primeuix/themes/lara';
import Nora from '@primeuix/themes/nora';
```

### Sistema de Cores

#### Paleta Primária

16 cores primárias disponíveis + opção "noir":

```typescript
const primaryColors = [
    'noir',      // Usa surface colors
    'emerald',   // Padrão
    'green', 'lime', 'orange', 'amber', 'yellow',
    'teal', 'cyan', 'sky', 'blue', 'indigo', 
    'violet', 'purple', 'fuchsia', 'pink', 'rose'
];
```

**Cor primária padrão**: `emerald`

#### Paleta de Surface

8 paletas de surface disponíveis, cada uma com 12 tonalidades (0, 50-950):

```typescript
const surfacePalettes = [
    'slate',    // Padrão light mode
    'gray',
    'zinc',     // Padrão dark mode
    'neutral',
    'stone',
    'soho',
    'viva',
    'ocean'
];
```

**Tonalidades de cada paleta**:
- `0`: Branco puro (#ffffff)
- `50`: Mais claro
- `100-900`: Gradação progressiva
- `950`: Mais escuro (quase preto)

#### Exemplo de paleta Surface (Slate)

```typescript
{
    name: 'slate',
    palette: {
        0: '#ffffff',
        50: '#f8fafc',
        100: '#f1f5f9',
        200: '#e2e8f0',
        300: '#cbd5e1',
        400: '#94a3b8',
        500: '#64748b',
        600: '#475569',
        700: '#334155',
        800: '#1e293b',
        900: '#0f172a',
        950: '#020617'
    }
}
```

### Design Tokens

O Sakai usa variáveis CSS do PrimeNG para garantir consistência:

```css
/* Cores principais */
--primary-color
--primary-50 até --primary-950

/* Superfície */
--surface-0 (background mais claro)
--surface-50 até --surface-950

/* Text */
--text-color
--text-muted-color
--text-color-secondary

/* Estado */
--highlight-background
--highlight-color

/* Bordas */
--border-color
--border-surface

/* Outros */
--rounded-border (border-radius padrão)
```

### Dark Mode

O dark mode é controlado pela classe `app-dark` no `documentElement`:

```typescript
// Ativar dark mode
document.documentElement.classList.add('app-dark');

// Desativar dark mode  
document.documentElement.classList.remove('app-dark');
```

**Suporte a View Transitions API** para transições suaves entre modos.

## 🏗️ Arquitetura de Layout

### Estrutura Principal

```
layout-wrapper
├── layout-topbar
│   ├── layout-topbar-logo-container
│   │   ├── layout-menu-button
│   │   └── layout-topbar-logo
│   └── layout-topbar-actions
│       ├── dark-mode-toggle
│       ├── theme-configurator
│       └── layout-topbar-menu
├── layout-sidebar
│   └── layout-menu
└── layout-main-container
    ├── layout-main
    │   └── router-outlet
    └── layout-footer
```

### Modos de Menu

**Static Mode** (padrão):
- Sidebar sempre visível em desktop
- Pode ser colapsada manualmente
- Em mobile vira overlay

**Overlay Mode**:
- Sidebar aparece como overlay
- Funciona igual em desktop e mobile

```typescript
layoutConfig = {
    preset: 'Aura',
    primary: 'emerald',
    surface: null,
    darkTheme: false,
    menuMode: 'static' // ou 'overlay'
};
```

### Breakpoint

Desktop vs Mobile determinado por: `window.innerWidth > 991`

### Estados do Layout

```typescript
interface LayoutState {
    staticMenuDesktopInactive: boolean;  // Menu colapsado em desktop (static mode)
    overlayMenuActive: boolean;          // Menu overlay ativo
    configSidebarVisible: boolean;       // Configurador visível
    mobileMenuActive: boolean;           // Menu mobile ativo
    menuHoverActive: boolean;            // Menu hover ativo
    activePath: string | null;           // Rota ativa
}
```

## 🧩 Componentes de Layout

### Topbar

**Classes principais**:
- `.layout-topbar` - Container principal
- `.layout-topbar-logo-container` - Container do logo e menu button
- `.layout-topbar-logo` - Logo (SVG + texto SAKAI)
- `.layout-topbar-action` - Botões de ação  
- `.layout-topbar-action-highlight` - Ação destacada
- `.layout-topbar-menu` - Menu de usuário

**Ações padrão**:
1. Toggle menu (hamburger)
2. Toggle dark mode (moon/sun icon)
3. Configurador de tema (palette icon)
4. Menu do usuário (Calendar, Messages, Profile)

**Logo SVG**: Usa `var(--primary-color)` para adaptação dinâmica

### Sidebar

**Classes principais**:
- `.layout-sidebar` - Container da sidebar
- `.layout-menu` - Lista de menu items
- `.menu-separator` - Separador de grupos

**Comportamento**:
- Fecha automaticamente ao navegar (mobile)
- Outside click detection
- Gerenciamento de overlay/static modes

### Menu System

**Estrutura hierárquica**:

```typescript
interface MenuItem {
    label?: string;
    icon?: string;
    routerLink?: string[];
    path?: string;
    items?: MenuItem[];
    separator?: boolean;
    url?: string;
    target?: string;
    class?: string;
}
```

**Exemplo de menu**:

```typescript
model: MenuItem[] = [
    {
        label: 'Home',
        items: [
            { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/'] }
        ]
    },
    {
        label: 'UI Components',
        items: [
            { label: 'Form Layout', icon: 'pi pi-fw pi-id-card', routerLink: ['/uikit/formlayout'] },
            { label: 'Input', icon: 'pi pi-fw pi-check-square', routerLink: ['/uikit/input'] },
            { label: 'Button', icon: 'pi pi-fw pi-mobile', routerLink: ['/uikit/button'] }
        ]
    }
];
```

### Footer

Minimalista, normalmente contém copyright e links relevantes.

## 📐 Sistema de Grid

Usa **TailwindCSS Grid** + **TailwindCSS PrimeUI**:

```html
<!-- Grid 12 colunas com gap -->
<div class="grid grid-cols-12 gap-8">
    <!-- 4 cards de estatísticas -->
    <app-stats-widget class="contents" />
    
    <!-- Coluna esquerda - lg screens -->
    <div class="col-span-12 xl:col-span-6">
        <app-recent-sales-widget />
        <app-best-selling-widget />
    </div>
    
    <!-- Coluna direita - lg screens -->
    <div class="col-span-12 xl:col-span-6">
        <app-revenue-stream-widget />
        <app-notifications-widget />
    </div>
</div>
```

**Breakpoints Tailwind**:
- `sm`: 640px
- `md`: 768px
- `lg`: 1024px
- `xl`: 1280px
- `2xl`: 1536px

## 🎯 Padrões de Componentes

### Widget Pattern

Widgets são componentes reutilizáveis, normalmente encapsulados em cards:

```typescript
@Component({
    selector: 'app-stats-widget',
    standalone: true,
    imports: [CommonModule, CardModule, ButtonModule],
    template: `
        <p-card class="h-full">
            <div class="flex items-center justify-between mb-4">
                <span class="text-muted-color font-semibold">{{ title }}</span>
                <i [class]="icon"></i>
            </div>
            <div class="text-4xl font-bold">{{ value }}</div>
            <div class="mt-2 flex items-center gap-2">
                <span [class]="changeClass">{{ change }}</span>
                <span class="text-sm text-muted-color">{{ period }}</span>
            </div>
        </p-card>
    `
})
export class StatsWidget {
    @Input() title: string;
    @Input() value: string;
    @Input() change: string;
    @Input() icon: string;
}
```

### Standalone Components

**SEMPRE use standalone components**:

```typescript
@Component({
    selector: 'app-example',
    standalone: true,
    imports: [CommonModule, ButtonModule, CardModule],
    template: `...`
})
```

### Signal-based State

Prefira **signals** ao invés de observables para state local:

```typescript
export class MyComponent {
    count = signal(0);
    doubled = computed(() => this.count() * 2);
    
    increment() {
        this.count.update(v => v + 1);
    }
}
```

### Service Injection

Use `inject()` function ao invés de constructor injection:

```typescript
export class MyComponent {
    layoutService = inject(LayoutService);
    router = inject(Router);
}
```

## 🎨 Estilos e Classes Utilitárias

### Classes de Texto

```css
.text-muted-color          /* Texto secundário/muted */
.text-color                /* Texto principal */
.font-semibold             /* Semi-negrito */
.text-sm, .text-base       /* Tamanhos */
.text-4xl                  /* Títulos grandes */
```

### Classes de Layout

```css
.flex, .grid               /* Layouts */
.items-center              /* Alinhamento vertical */
.justify-between           /* Espaçamento horizontal */
.gap-2, .gap-4, .gap-8     /* Espaçamentos */
.p-4, .pt-2, .mb-4         /* Padding/margin */
```

### Classes de Superfície

```css
.bg-surface-0              /* Background claro */
.bg-surface-900            /* Background escuro */
.border-surface            /* Bordas com cor surface */
.rounded-border            /* Border-radius padrão */
```

### Classes de Animação

```css
.animate-scalein           /* Scale in animation */
.animate-fadeout           /* Fade out animation */
```

### Classes Customizadas do Sakai

```css
/* Layout states */
.layout-wrapper
.layout-overlay
.layout-static
.layout-static-inactive
.layout-overlay-active
.layout-mobile-active

/* Bloqueio de scroll */
.blocked-scroll
```

## 📦 Pacotes e Dependências Essenciais

```json
{
    "dependencies": {
        "@angular/common": "^21",
        "@angular/core": "^21",
        "@angular/forms": "^21",
        "@angular/router": "^21",
        "@primeuix/themes": "^2.0.0",
        "@tailwindcss/postcss": "^4.1.11",
        "primeng": "^21.0.2",
        "primeicons": "^7.0.0",
        "tailwindcss-primeui": "^0.6.1",
        "tailwindcss": "^4.1.11"
    }
}
```

## 🛠️ Configurador de Tema

O Sakai inclui um configurador visual completo:

**Funcionalidades**:
1. Seleção de cor primária (16 opções + noir)
2. Seleção de surface palette (8 opções)
3. Alternância entre presets (Aura, Lara, Nora)
4. Alternância de menu mode (Static, Overlay)

**APIs utilizadas**:

```typescript
import { $t, updatePreset, updateSurfacePalette } from '@primeuix/themes';

// Atualizar preset
updatePreset(presetExtension);

// Atualizar surface
updateSurfacePalette(surfacePalette);

// Aplicar tudo
$t()
    .preset(Aura)
    .preset(presetExtension)
    .surfacePalette(surfacePalette)
    .use({ useDefaultOptions: true });
```

## 📱 Responsividade

### Mobile First

Sempre pense mobile-first com progressive enhancement:

```html
<!-- Mobile: col-span-12, Desktop: col-span-6 -->
<div class="col-span-12 xl:col-span-6">
    ...
</div>

<!-- Esconder em mobile, mostrar em lg+ -->
<div class="hidden lg:block">
    ...
</div>
```

### Menu Comportamento

**Mobile** (`<= 991px`):
- Menu hamburger sempre visível
- Sidebar como overlay
- Outside click fecha menu
- Navegação fecha menu automaticamente

**Desktop** (`> 991px`):
- Static mode: sidebar sempre visível, pode colapsar
- Overlay mode: sidebar como overlay

## 🚀 Boas Práticas

### 1. Estrutura de Componentes

```
src/app/
├── layout/
│   ├── component/
│   │   ├── app.layout.ts
│   │   ├── app.topbar.ts
│   │   ├── app.sidebar.ts
│   │   ├── app.menu.ts
│   │   ├── app.menuitem.ts
│   │   ├── app.footer.ts
│   │   ├── app.configurator.ts
│   │   └── app.floatingconfigurator.ts
│   └── service/
│       └── layout.service.ts
├── pages/
│   ├── dashboard/
│   │   ├── dashboard.ts
│   │   └── components/
│   │       ├── statswidget.ts
│   │       ├── recentsaleswidget.ts
│   │       └── ...
│   ├── uikit/
│   ├── auth/
│   └── ...
└── app.config.ts
```

### 2. Nomenclatura de Arquivos

- **Componentes**: `componentname.ts` (lowercase, sem hífen)
- **Services**: `service-name.service.ts`
- **Interfaces**: `interface-name.interface.ts`

### 3. Imports

Organize imports por grupos:

```typescript
// Angular core
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// PrimeNG
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

// App
import { LayoutService } from '@/app/layout/service/layout.service';
```

### 4. Standalone Components

Todos os componentes devem ser standalone:

```typescript
@Component({
    selector: 'app-my-component',
    standalone: true,
    imports: [CommonModule, ButtonModule, CardModule],
    template: `...`
})
```

### 5. TypeScript Path Mapping

Configure em `tsconfig.json`:

```json
{
    "compilerOptions": {
        "paths": {
            "@/*": ["./src/*"]
        }
    }
}
```

Uso:

```typescript
import { LayoutService } from '@/app/layout/service/layout.service';
```

### 6. Template Inline vs External

**Use inline para componentes pequenos**:

```typescript
@Component({
    template: `<div>...</div>`
})
```

**Use external para templates grandes** (>30 linhas):

```typescript
@Component({
    templateUrl: './component.html'
})
```

### 7. Zoneless Detection

O Sakai usa `provideZonelessChangeDetection()` para performance:

```typescript
export const appConfig: ApplicationConfig = {
    providers: [
        provideZonelessChangeDetection()
    ]
};
```

**Implicação**: Use signals e OnPush strategy para change detection eficiente

## 🎯 Componentes PrimeNG Mais Usados

### Form Components

```typescript
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputTextareaModule } from 'primeng/inputtextarea';
```

### Data Components

```typescript
import { TableModule } from 'primeng/table';
import { TreeModule } from 'primeng/tree';
import { TreeTableModule } from 'primeng/treetable';
import { DataViewModule } from 'primeng/dataview';
```

### Panel Components

```typescript
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { AccordionModule } from 'primeng/accordion';
import { TabsModule } from 'primeng/tabs';
import { ToolbarModule } from 'primeng/toolbar';
```

### Overlay Components

```typescript
import { DialogModule } from 'primeng/dialog';
import { DrawerModule } from 'primeng/drawer';
import { OverlayModule } from 'primeng/overlay';
import { TooltipModule } from 'primeng/tooltip';
```

### Menu Components

```typescript
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { TieredMenuModule } from 'primeng/tieredmenu';
```

### Misc Components

```typescript
import { ChartModule } from 'primeng/chart';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';
import { ProgressBarModule } from 'primeng/progressbar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
```

## 📝 Exemplos de Uso

### Criar um novo Widget

```typescript
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
    selector: 'app-sales-widget',
    standalone: true,
    imports: [CommonModule, CardModule, ButtonModule],
    template: `
        <p-card class="h-full">
            <div class="flex items-center justify-between mb-4">
                <div>
                    <div class="text-muted-color font-semibold text-sm">{{ title }}</div>
                    <div class="text-4xl font-bold mt-2">{{ value }}</div>
                </div>
                <i [class]="icon" class="text-primary text-4xl"></i>
            </div>
            <div class="flex items-center gap-2 mt-4">
                <span [ngClass]="changeClass" class="font-semibold">{{ change }}</span>
                <span class="text-sm text-muted-color">{{ period }}</span>
            </div>
            <p-button label="Ver Detalhes" [outlined]="true" size="small" class="mt-4 w-full" />
        </p-card>
    `
})
export class SalesWidget {
    @Input() title: string = 'Total Sales';
    @Input() value: string = '0';
    @Input() change: string = '+0%';
    @Input() period: string = 'this month';
    @Input() icon: string = 'pi pi-shopping-cart';
    
    get changeClass() {
        return this.change.startsWith('+') 
            ? 'text-green-500' 
            : 'text-red-500';
    }
}
```

### Criar uma página com Grid

```typescript
import { Component } from '@angular/core';
import { SalesWidget } from './components/saleswidget';
import { RevenueChart } from './components/revenuechart';
import { RecentOrders } from './components/recentorders';

@Component({
    selector: 'app-analytics',
    standalone: true,
    imports: [SalesWidget, RevenueChart, RecentOrders],
    template: `
        <div class="grid grid-cols-12 gap-8">
            <!-- Stats Cards -->
            <div class="col-span-12 md:col-span-6 xl:col-span-3">
                <app-sales-widget 
                    title="Total Sales" 
                    value="$45,234" 
                    change="+12.5%" 
                    icon="pi pi-shopping-cart" 
                />
            </div>
            <div class="col-span-12 md:col-span-6 xl:col-span-3">
                <app-sales-widget 
                    title="Revenue" 
                    value="$128,450" 
                    change="+8.3%" 
                    icon="pi pi-dollar" 
                />
            </div>
            <div class="col-span-12 md:col-span-6 xl:col-span-3">
                <app-sales-widget 
                    title="Customers" 
                    value="2,847" 
                    change="+5.2%" 
                    icon="pi pi-users" 
                />
            </div>
            <div class="col-span-12 md:col-span-6 xl:col-span-3">
                <app-sales-widget 
                    title="Orders" 
                    value="1,234" 
                    change="-2.1%" 
                    icon="pi pi-chart-line" 
                />
            </div>
            
            <!-- Charts -->
            <div class="col-span-12 xl:col-span-8">
                <app-revenue-chart />
            </div>
            
            <!-- Recent Orders -->
            <div class="col-span-12 xl:col-span-4">
                <app-recent-orders />
            </div>
        </div>
    `
})
export class AnalyticsPage {}
```

### Usar o Layout Service

```typescript
import { Component, inject } from '@angular/core';
import { LayoutService } from '@/app/layout/service/layout.service';

@Component({
    selector: 'app-theme-toggle',
    standalone: true,
    template: `
        <button (click)="toggleTheme()">
            <i [class]="isDark() ? 'pi pi-sun' : 'pi pi-moon'"></i>
        </button>
    `
})
export class ThemeToggle {
    layoutService = inject(LayoutService);
    
    isDark = this.layoutService.isDarkTheme;
    
    toggleTheme() {
        this.layoutService.layoutConfig.update(state => ({
            ...state,
            darkTheme: !state.darkTheme
        }));
    }
}
```

## 🔌 Integração com API REST (Spring Boot)

### Interface PageResponse

Interface padrão para consumir endpoints paginados do Spring Boot:

```typescript
interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
```

### Environment

```typescript
// src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api/v1',
};

// src/environments/environment.prod.ts
export const environment = {
  production: true,
  apiUrl: '/api/v1',
};
```

### Padrão de Service CRUD

```typescript
@Injectable({ providedIn: 'root' })
export class {{Entidade}}Service {
  private readonly http = inject(HttpClient);
  private readonly API = `${environment.apiUrl}/{{recurso}}`;

  findAll(page = 0, size = 10, search?: string): Observable<PageResponse<{{Entidade}}>> {
    let params = `?page=${page}&size=${size}&sort=createdAt,desc`;
    if (search?.trim()) params += `&search=${encodeURIComponent(search.trim())}`;
    return this.http.get<PageResponse<{{Entidade}}>>(`${this.API}${params}`);
  }

  findById(id: string): Observable<{{Entidade}}> {
    return this.http.get<{{Entidade}}>(`${this.API}/${id}`);
  }

  create(data: {{Entidade}}Request): Observable<{{Entidade}}> {
    return this.http.post<{{Entidade}}>(this.API, data);
  }

  update(id: string, data: {{Entidade}}Request): Observable<{{Entidade}}> {
    return this.http.put<{{Entidade}}>(`${this.API}/${id}`, data);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}
```

### Configuração HttpClient com Interceptor

```typescript
// app.config.ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from '@/app/core/interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    // ... demais providers
  ]
};
```

## 🔐 Autenticação JWT

### Estrutura de Arquivos Auth

```
src/app/
├── core/
│   ├── services/
│   │   └── auth.service.ts          # Login, logout, refresh, token storage
│   ├── guards/
│   │   └── auth.guard.ts            # authGuard + guestGuard (CanActivateFn)
│   └── interceptors/
│       └── auth.interceptor.ts      # JWT Bearer HttpInterceptorFn
├── pages/
│   └── auth/
│       └── login.ts                 # Tela de login
```

### AuthService (signal-based)

- Usa `inject()` (sem constructor injection)
- Estado `isAuthenticated` via `signal` + `computed`
- Armazena `access_token` e `refresh_token` no `localStorage`
- Suporta login, register, refresh e logout

### Guards Funcionais

```typescript
// authGuard — protege rotas autenticadas
export const authGuard: CanActivateFn = () => { ... };

// guestGuard — protege login (redireciona se já autenticado)
export const guestGuard: CanActivateFn = () => { ... };
```

### Interceptor JWT

Adiciona automaticamente `Authorization: Bearer <token>` em todas as requests HTTP.

### Rotas com Auth

```typescript
export const routes: Routes = [
  {
    path: 'auth',
    canActivate: [guestGuard],
    children: [
      { path: 'login', loadComponent: () => import('@/app/pages/auth/login').then(m => m.Login) },
    ],
  },
  {
    path: '',
    component: AppLayout,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadComponent: () => import('@/app/pages/dashboard/dashboard').then(m => m.Dashboard) },
      // Módulos CRUD com lazy loading...
    ],
  },
  { path: '**', redirectTo: '' },
];
```

## 📋 Templates CRUD (PrimeNG)

### Passo a Passo — Novo Módulo CRUD Frontend

```
1. pages/{{modulo}}/{{modulo}}-list.ts      ← Listagem paginada com p-table
2. pages/{{modulo}}/{{modulo}}-form.ts      ← Formulário create/edit
3. app.routes.ts → adicionar rotas (list, novo, :id/editar)
4. Menu sidebar → adicionar item no model[]
```

### Templates Disponíveis

| Arquivo | Descrição |
|---------|-----------|
| `crud-list.ts.tmpl` | Lista paginada com p-table, busca, p-tag status, confirm delete, Toast |
| `crud-form.ts.tmpl` | Formulário create/edit com FloatLabel, validação, p-select, p-datepicker, Toast |
| `auth-service.ts.tmpl` | AuthService JWT com signals e inject() |
| `auth-guard.ts.tmpl` | authGuard + guestGuard (CanActivateFn) |
| `auth-interceptor.ts.tmpl` | JWT Bearer HttpInterceptorFn |
| `app-routes.ts.tmpl` | Rotas com lazy loading, auth guards, padrão CRUD |

### Componentes PrimeNG usados nos templates CRUD

```typescript
// List
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToolbarModule } from 'primeng/toolbar';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

// Form
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { InputNumberModule } from 'primeng/inputnumber';
import { FloatLabelModule } from 'primeng/floatlabel';
import { CardModule } from 'primeng/card';
```

## ✅ Checklist de Implementação

Ao criar um novo projeto baseado no Sakai:

### Setup Inicial
- [ ] Instalar dependências: Angular 21+, PrimeNG 21+, TailwindCSS 4+, @primeuix/themes
- [ ] Configurar `app.config.ts` com preset Aura e dark mode selector
- [ ] Configurar TailwindCSS com `tailwindcss-primeui` plugin
- [ ] Configurar path mapping `@/*` em `tsconfig.json`
- [ ] Habilitar `provideZonelessChangeDetection()`

### Layout
- [ ] Criar `LayoutService` com signals para config e state
- [ ] Implementar componente `AppLayout` com topbar, sidebar, router-outlet, footer
- [ ] Implementar `AppTopbar` com logo, menu toggle, dark mode, configurador
- [ ] Implementar `AppSidebar` com menu hierárquico
- [ ] Implementar `AppMenu` e `AppMenuitem` com suporte a nested items
- [ ] Implementar `AppConfigurator` com seletor de cores e presets
- [ ] Adicionar suporte a static/overlay modes
- [ ] Implementar responsividade mobile (<= 991px)

### Theming
- [ ] Configurar primary color padrão (emerald)
- [ ] Configurar surface palettes (slate light, zinc dark)
- [ ] Implementar dark mode toggle com View Transitions
- [ ] Testar alternância entre presets (Aura, Lara, Nora)
- [ ] Validar design tokens em todos os componentes

### Componentes
- [ ] Criar componentes standalone
- [ ] Usar signals para state management
- [ ] Usar inject() para dependency injection
- [ ] Usar template inline para componentes pequenos
- [ ] Seguir padrão de widgets para cards/painéis
- [ ] Usar TailwindCSS classes + design tokens

### Autenticação
- [ ] Criar `AuthService` com JWT (login, logout, refresh, signals)
- [ ] Criar `authGuard` e `guestGuard` (CanActivateFn)
- [ ] Criar `authInterceptor` (JWT Bearer HttpInterceptorFn)
- [ ] Configurar `provideHttpClient(withInterceptors([authInterceptor]))` no `app.config.ts`
- [ ] Criar página de Login em `pages/auth/login.ts`
- [ ] Configurar rotas com `authGuard` e `guestGuard`

### Módulos CRUD
- [ ] Para cada módulo:
    - [ ] Criar `pages/{{modulo}}/{{modulo}}-list.ts` (p-table paginada + busca + status)
    - [ ] Criar `pages/{{modulo}}/{{modulo}}-form.ts` (create/edit com validação + Toast)
    - [ ] Adicionar rotas (list, novo, :id/editar) com lazy loading
    - [ ] Adicionar item no menu da sidebar

### Performance
- [ ] Habilitar zoneless change detection
- [ ] Usar `computed()` para valores derivados
- [ ] Lazy load de rotas quando possível
- [ ] Otimizar imports (não importar módulos inteiros)

### Acessibilidade
- [ ] Usar componentes PrimeNG (já acessíveis por padrão)
- [ ] Adicionar labels adequados em formulários
- [ ] Testar navegação por teclado
- [ ] Verificar contrast ratio das cores customizadas

## 🔗 Recursos Úteis

- **PrimeNG Docs**: https://primeng.org
- **@primeuix/themes**: https://www.npmjs.com/package/@primeuix/themes
- **Sakai GitHub**: https://github.com/primefaces/sakai-ng
- **TailwindCSS PrimeUI**: https://www.npmjs.com/package/tailwindcss-primeui
- **Angular Signals**: https://angular.dev/guide/signals

## 📄 Licença

Este design system é baseado no template Sakai, que possui licença própria da PrimeTek. Consulte o repositório oficial para detalhes.

---

**Versão**: 1.0.0  
**Última atualização**: 2025  
**Compatível com**: PrimeNG 21+, Angular 21+
