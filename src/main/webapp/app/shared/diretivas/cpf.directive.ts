import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';
import { CpfValidador } from 'app/shared/validadores/cpf-validador';

@Directive({
    selector: '[jhiCpf]',
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: CpfDirective,
            multi: true
        }
    ]
})
export class CpfDirective implements Validator {
    /**
     * Valida os dados.
     *
     * @param AbstractControl control
     * @return object ou null caso válido
     */
    validate(control: AbstractControl): { [key: string]: any } {
        return CpfValidador.validate(control);
    }
}
