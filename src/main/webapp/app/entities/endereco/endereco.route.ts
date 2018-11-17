import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Endereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { EnderecoComponent } from './endereco.component';
import { EnderecoDetailComponent } from './endereco-detail.component';
import { EnderecoUpdateComponent } from './endereco-update.component';
import { EnderecoDeletePopupComponent } from './endereco-delete-dialog.component';
import { IEndereco } from 'app/shared/model/endereco.model';

@Injectable({ providedIn: 'root' })
export class EnderecoResolve implements Resolve<IEndereco> {
    constructor(private service: EnderecoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Endereco> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Endereco>) => response.ok),
                map((endereco: HttpResponse<Endereco>) => endereco.body)
            );
        }
        return of(new Endereco());
    }
}

export const enderecoRoute: Routes = [
    {
        path: 'endereco',
        component: EnderecoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.endereco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'endereco/:id/view',
        component: EnderecoDetailComponent,
        resolve: {
            endereco: EnderecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.endereco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'endereco/new',
        component: EnderecoUpdateComponent,
        resolve: {
            endereco: EnderecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.endereco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'endereco/:id/edit',
        component: EnderecoUpdateComponent,
        resolve: {
            endereco: EnderecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.endereco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enderecoPopupRoute: Routes = [
    {
        path: 'endereco/:id/delete',
        component: EnderecoDeletePopupComponent,
        resolve: {
            endereco: EnderecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jnsFinanceiroApp.endereco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
