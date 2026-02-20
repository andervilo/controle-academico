import { Injectable, signal, computed } from '@angular/core';

export interface LayoutConfig {
    preset: string;
    primary: string;
    darkTheme: boolean;
    menuMode: 'static' | 'overlay';
}

export interface LayoutState {
    staticMenuDesktopInactive: boolean;
    mobileMenuActive: boolean;
}

@Injectable({ providedIn: 'root' })
export class LayoutService {
    layoutConfig = signal<LayoutConfig>({
        preset: 'Aura',
        primary: 'emerald',
        darkTheme: false,
        menuMode: 'static',
    });

    layoutState = signal<LayoutState>({
        staticMenuDesktopInactive: false,
        mobileMenuActive: false,
    });

    isDarkTheme = computed(() => this.layoutConfig().darkTheme);

    isMobile(): boolean {
        return window.innerWidth <= 991;
    }

    toggleMenu(): void {
        if (this.isMobile()) {
            this.layoutState.update((s) => ({ ...s, mobileMenuActive: !s.mobileMenuActive }));
        } else {
            this.layoutState.update((s) => ({ ...s, staticMenuDesktopInactive: !s.staticMenuDesktopInactive }));
        }
    }

    toggleDarkMode(): void {
        const newDark = !this.layoutConfig().darkTheme;
        this.layoutConfig.update((c) => ({ ...c, darkTheme: newDark }));
        if (newDark) {
            document.documentElement.classList.add('app-dark');
        } else {
            document.documentElement.classList.remove('app-dark');
        }
    }

    onMenuItemClick(): void {
        if (this.isMobile()) {
            this.layoutState.update((s) => ({ ...s, mobileMenuActive: false }));
        }
    }

    containerClass = computed(() => {
        const config = this.layoutConfig();
        const state = this.layoutState();
        return {
            'layout-overlay': config.menuMode === 'overlay',
            'layout-static': config.menuMode === 'static',
            'layout-static-inactive': config.menuMode === 'static' && state.staticMenuDesktopInactive,
            'layout-mobile-active': state.mobileMenuActive,
        };
    });
}
