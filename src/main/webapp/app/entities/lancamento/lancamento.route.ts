import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { LancamentoComponent } from './lancamento.component';
import { LancamentoDetailComponent } from './lancamento-detail.component';
import { LancamentoUpdateComponent } from './lancamento-update.component';
import { LancamentoDeletePopupComponent } from './lancamento-delete-dialog.component';
import { ILancamento } from 'app/shared/model/lancamento.model';

@Injectable({ providedIn: 'root' })
export class LancamentoResolve implements Resolve<ILancamento> {
    constructor(private service: LancamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lancamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lancamento>) => response.ok),
                map((lancamento: HttpResponse<Lancamento>) => lancamento.body)
            );
        }
        return of(new Lancamento());
    }
}

export const lancamentoRoute: Routes = [
    {
        path: 'lancamento',
        component: LancamentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jnsFinanceiroApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/:id/view',
        component: LancamentoDetailComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/new',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lancamento/:id/edit',
        component: LancamentoUpdateComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoPopupRoute: Routes = [
    {
        path: 'lancamento/:id/delete',
        component: LancamentoDeletePopupComponent,
        resolve: {
            lancamento: LancamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
