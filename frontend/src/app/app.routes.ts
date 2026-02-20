import { Routes } from '@angular/router';
import { AppLayout } from '@/app/layout/component/app.layout';

export const routes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
            {
                path: 'dashboard',
                loadComponent: () => import('@/app/pages/dashboard/dashboard').then((m) => m.Dashboard),
            },

            // ── Alunos ──
            {
                path: 'alunos',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/alunos/aluno-list').then((m) => m.AlunoListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/alunos/aluno-form').then((m) => m.AlunoFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/alunos/aluno-form').then((m) => m.AlunoFormComponent) },
                ],
            },

            // ── Professores ──
            {
                path: 'professores',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/professores/professor-list').then((m) => m.ProfessorListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/professores/professor-form').then((m) => m.ProfessorFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/professores/professor-form').then((m) => m.ProfessorFormComponent) },
                ],
            },

            // ── Disciplinas ──
            {
                path: 'disciplinas',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/disciplinas/disciplina-list').then((m) => m.DisciplinaListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/disciplinas/disciplina-form').then((m) => m.DisciplinaFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/disciplinas/disciplina-form').then((m) => m.DisciplinaFormComponent) },
                ],
            },

            // ── Cursos ──
            {
                path: 'cursos',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/cursos/curso-list').then((m) => m.CursoListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/cursos/curso-form').then((m) => m.CursoFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/cursos/curso-form').then((m) => m.CursoFormComponent) },
                ],
            },

            // ── Responsáveis Financeiros ──
            {
                path: 'responsaveis',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/responsaveis/responsavel-list').then((m) => m.ResponsavelListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/responsaveis/responsavel-form').then((m) => m.ResponsavelFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/responsaveis/responsavel-form').then((m) => m.ResponsavelFormComponent) },
                ],
            },

            // ── Ano Letivo ──
            {
                path: 'anos-letivos',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/anos-letivos/ano-letivo-list').then((m) => m.AnoLetivoListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/anos-letivos/ano-letivo-form').then((m) => m.AnoLetivoFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/anos-letivos/ano-letivo-form').then((m) => m.AnoLetivoFormComponent) },
                ],
            },

            // ── Turmas ──
            {
                path: 'turmas',
                children: [
                    { path: '', loadComponent: () => import('@/app/pages/turmas/turma-list').then((m) => m.TurmaListComponent) },
                    { path: 'novo', loadComponent: () => import('@/app/pages/turmas/turma-form').then((m) => m.TurmaFormComponent) },
                    { path: ':id/editar', loadComponent: () => import('@/app/pages/turmas/turma-form').then((m) => m.TurmaFormComponent) },
                ],
            },

            // ── Grade Horária ──
            {
                path: 'grade-horaria',
                loadComponent: () => import('@/app/pages/grade-horaria/grade-horaria').then((m) => m.GradeHorariaComponent),
            },

            // ── Disponibilidade ──
            {
                path: 'disponibilidade',
                loadComponent: () => import('@/app/pages/disponibilidade/disponibilidade').then((m) => m.DisponibilidadeComponent),
            },
        ],
    },
    { path: '**', redirectTo: '' },
];
