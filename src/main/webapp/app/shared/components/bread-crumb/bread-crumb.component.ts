import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-bread-crumb',
    templateUrl: './bread-crumb.component.html',
    styles: []
})
export class BreadCrumbComponent implements OnInit {
    @Input()
    items: BreadCrumbItem[] = [];

    constructor() {}

    ngOnInit() {}

    isThelastItem(item: BreadCrumbItem): boolean {
        const index = this.items.indexOf(item);
        return index + 1 === this.items.length;
    }

    translate(text: string, currentAction?: string) {
        return currentAction === 'new'
            ? `jnsFinanceiroApp.${text}.home.createLabel`
            : currentAction === 'edit'
                ? `jnsFinanceiroApp.${text}.home.editLabel`
                : `jnsFinanceiroApp.${text}.home.title`;
    }
}

interface BreadCrumbItem {
    text?: string;
    link?: string;
    currentAction?: string;
}
