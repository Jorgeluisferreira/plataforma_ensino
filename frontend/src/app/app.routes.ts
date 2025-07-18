import { Routes } from '@angular/router';

export const routes: Routes = [{
    path:'',
    pathMatch:'full',
    loadComponent: () => {
        return import('./home/home.component').then(m => m.HomeComponent)
    },
},
{
    path:'register',
    pathMatch:'full',
    loadComponent: () => {
        return import('./registration/registration.component').then(m => m.RegistrationComponent)
    },
},
{
    path:'areaUsuario',
    pathMatch:'full',
    loadComponent: () => {
        return import('./area-usuario/area-usuario.component').then(m => m.AreaUsuarioComponent)
    },
},
{
    path:'pagamento',
    pathMatch:'full',
    loadComponent: () => {
        return import('./payment/payment.component').then(m => m.PaymentComponent)
    },
},
{
    path:'curso/:id',
    pathMatch:'full',
    loadComponent: () => {
        return import('./curso/curso.component').then(m => m.CursoComponent)
    },
},
{
    path:'obrigado',
    pathMatch:'full',
    loadComponent: () => {
        return import('./obrigado/obrigado.component').then(m => m.ObrigadoComponent)
    },
},
];
