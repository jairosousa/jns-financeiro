import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'telefone'
})
export class TelefonePipe implements PipeTransform {
    transform(telefone: string): string {
        let str = telefone + '';

        str = str.replace(/\D/g, '');

        if (str.length === 11) {
            str = str.replace(/^(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        } else {
            str = str.replace(/^(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
        }

        return str;
    }
}
