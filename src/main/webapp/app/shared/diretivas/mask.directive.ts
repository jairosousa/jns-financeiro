import { Directive, ElementRef, HostListener, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
    selector: '[jhiMask]',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: MaskDirective,
            multi: true
        }
    ]
})
export class MaskDirective implements ControlValueAccessor {
    onTouched: any;
    onChange: any;

    // tslint:disable-next-line:no-input-rename
    @Input('jhiMask')
    jhiMask: string;

    constructor(private el: ElementRef) {}

    writeValue(value: any): void {
        if (value) {
            this.el.nativeElement.value = this.aplicarMascara(value);
        }
    }

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    @HostListener('keyup', ['$event'])
    onKeyup($event: any) {
        const valor = $event.target.value.replace(/\D/g, '');

        // retorna caso pressionado backspace
        if ($event.keyCode === 8) {
            this.onChange(valor);
            return;
        }

        const pad = this.jhiMask.replace(/\D/g, '').replace(/9/g, '_');
        if (valor.length <= pad.length) {
            this.onChange(valor);
        }

        $event.target.value = this.aplicarMascara(valor);
    }

    @HostListener('blur', ['$event'])
    onBlur($event: any) {
        if ($event.target.value.length === this.jhiMask.length) {
            return;
        }
        this.onChange('');
        $event.target.value = '';
    }

    /**
     * Aplica a máscara a determinado valor.
     *
     * @param string valor
     * @return string
     */
    aplicarMascara(valor: string): string {
        valor = valor.replace(/\D/g, '');
        const pad = this.jhiMask.replace(/\D/g, '').replace(/9/g, '_');
        const valorMask = valor + pad.substring(0, pad.length - valor.length);
        let valorMaskPos = 0;

        valor = '';
        for (let i = 0; i < this.jhiMask.length; i++) {
            // tslint:disable-next-line:radix
            if (isNaN(parseInt(this.jhiMask.charAt(i)))) {
                valor += this.jhiMask.charAt(i);
            } else {
                valor += valorMask[valorMaskPos++];
            }
        }

        if (valor.indexOf('_') > -1) {
            valor = valor.substr(0, valor.indexOf('_'));
        }

        return valor;
    }
}
