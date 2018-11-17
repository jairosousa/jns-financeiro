import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { FornecedorComponent } from './fornecedor.component';
import { FornecedorDetailComponent } from './fornecedor-detail.component';
import { FornecedorUpdateComponent } from './fornecedor-update.component';
import { FornecedorDeletePopupComponent } from './fornecedor-delete-dialog.component';
import { IFornecedor } from 'app/shared/model/fornecedor.model';

@Injectable({ providedIn: 'root' })
export class FornecedorResolve implements Resolve<IFornecedor> {
    constructor(private service: FornecedorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Fornecedor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fornecedor>) => response.ok),
                map((fornecedor: HttpResponse<Fornecedor>) => fornecedor.body)
            );
        }
        return of(new Fornecedor());
    }
}

export const fornecedorRoute: Routes = [
    {
        path: 'fornecedor',
        component: FornecedorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jnsFinanceiroApp.fornecedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fornecedor/:id/view',
        component: FornecedorDetailComponent,
        resolve: {
            fornecedor: FornecedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.fornecedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fornecedor/new',
        component: FornecedorUpdateComponent,
        resolve: {
            fornecedor: FornecedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.fornecedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fornecedor/:id/edit',
        component: FornecedorUpdateComponent,
        resolve: {
            fornecedor: FornecedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.fornecedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fornecedorPopupRoute: Routes = [
    {
        path: 'fornecedor/:id/delete',
        component: FornecedorDeletePopupComponent,
        resolve: {
            fornecedor: FornecedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.fornecedor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
