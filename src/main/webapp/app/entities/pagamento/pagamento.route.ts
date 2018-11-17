import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from './pagamento.service';
import { PagamentoComponent } from './pagamento.component';
import { PagamentoDetailComponent } from './pagamento-detail.component';
import { PagamentoUpdateComponent } from './pagamento-update.component';
import { PagamentoDeletePopupComponent } from './pagamento-delete-dialog.component';
import { IPagamento } from 'app/shared/model/pagamento.model';

@Injectable({ providedIn: 'root' })
export class PagamentoResolve implements Resolve<IPagamento> {
    constructor(private service: PagamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Pagamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Pagamento>) => response.ok),
                map((pagamento: HttpResponse<Pagamento>) => pagamento.body)
            );
        }
        return of(new Pagamento());
    }
}

export const pagamentoRoute: Routes = [
    {
        path: 'pagamento',
        component: PagamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.pagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pagamento/:id/view',
        component: PagamentoDetailComponent,
        resolve: {
            pagamento: PagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.pagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pagamento/new',
        component: PagamentoUpdateComponent,
        resolve: {
            pagamento: PagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.pagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pagamento/:id/edit',
        component: PagamentoUpdateComponent,
        resolve: {
            pagamento: PagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.pagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pagamentoPopupRoute: Routes = [
    {
        path: 'pagamento/:id/delete',
        component: PagamentoDeletePopupComponent,
        resolve: {
            pagamento: PagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.pagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
