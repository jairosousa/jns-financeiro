import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'capitalLetter'
})
export class CapitalLetterPipe implements PipeTransform {
    transform(value: string): string {
        if (!value) {
            return null;
        }

        if (value && typeof value === 'string') {
            return value[0].toUpperCase() + value.slice(1);
        }
    }
}
