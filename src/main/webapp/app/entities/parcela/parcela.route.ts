import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Parcela } from 'app/shared/model/parcela.model';
import { ParcelaService } from './parcela.service';
import { ParcelaComponent } from './parcela.component';
import { ParcelaDetailComponent } from './parcela-detail.component';
import { ParcelaUpdateComponent } from './parcela-update.component';
import { ParcelaDeletePopupComponent } from './parcela-delete-dialog.component';
import { IParcela } from 'app/shared/model/parcela.model';
@Injectable({ providedIn: 'root' })
export class ParcelaResolve implements Resolve<IParcela> {
    constructor(private service: ParcelaService) {}
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Parcela> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Parcela>) => response.ok),
                map((parcela: HttpResponse<Parcela>) => parcela.body)
            );
        }
        return of(new Parcela());
    }
}
export const parcelaRoute: Routes = [
    {
        path: 'parcela',
        component: ParcelaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.parcela.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parcela/:id/view',
        component: ParcelaDetailComponent,
        resolve: {
            parcela: ParcelaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.parcela.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parcela/new',
        component: ParcelaUpdateComponent,
        resolve: {
            parcela: ParcelaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.parcela.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parcela/:id/edit',
        component: ParcelaUpdateComponent,
        resolve: {
            parcela: ParcelaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.parcela.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
export const parcelaPopupRoute: Routes = [
    {
        path: 'parcela/:id/delete',
        component: ParcelaDeletePopupComponent,
        resolve: {
            parcela: ParcelaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.parcela.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
