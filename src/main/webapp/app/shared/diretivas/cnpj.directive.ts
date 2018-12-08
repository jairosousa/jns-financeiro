import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';
import { CnpjValidador } from 'app/shared/validadores/cnpj-validador';

@Directive({
    selector: '[jhiCnpj]',
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: CnpjDirective,
            multi: true
        }
    ]
})
export class CnpjDirective implements Validator {
    /**
     * Valida os dados.
     *
     * @param AbstractControl control
     * @return object ou null caso válido
     */
    validate(control: AbstractControl): { [key: string]: any } {
        return CnpjValidador.validate(control);
    }
}
