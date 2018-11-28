import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cartao } from 'app/shared/model/cartao.model';
import { CartaoService } from './cartao.service';
import { CartaoComponent } from './cartao.component';
import { CartaoDetailComponent } from './cartao-detail.component';
import { CartaoUpdateComponent } from './cartao-update.component';
import { CartaoDeletePopupComponent } from './cartao-delete-dialog.component';
import { ICartao } from 'app/shared/model/cartao.model';

@Injectable({ providedIn: 'root' })
export class CartaoResolve implements Resolve<ICartao> {
    constructor(private service: CartaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cartao> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cartao>) => response.ok),
                map((cartao: HttpResponse<Cartao>) => cartao.body)
            );
        }
        return of(new Cartao());
    }
}

export const cartaoRoute: Routes = [
    {
        path: 'cartao',
        component: CartaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.cartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cartao/:id/view',
        component: CartaoDetailComponent,
        resolve: {
            cartao: CartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.cartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cartao/new',
        component: CartaoUpdateComponent,
        resolve: {
            cartao: CartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.cartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cartao/:id/edit',
        component: CartaoUpdateComponent,
        resolve: {
            cartao: CartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.cartao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartaoPopupRoute: Routes = [
    {
        path: 'cartao/:id/delete',
        component: CartaoDeletePopupComponent,
        resolve: {
            cartao: CartaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.cartao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
