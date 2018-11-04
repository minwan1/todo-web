import { IComponentOptions } from 'angular';

import { demoData } from '../shared/index';

export class ColumnBindingEgComponent implements IComponentOptions {
    controller = ColumnBindingEgController;
    templateUrl = './column-binding-eg.html';
}
class ColumnBindingEgController {
    data = demoData;
}
