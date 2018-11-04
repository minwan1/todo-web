import * as ng1 from 'angular';
import { ngTableCoreModule } from './src/core/index';
import { ngTableBrowserModule } from './src/browser/index';

const ngTableModule = ng1.module('ngTable', [ngTableCoreModule.name, ngTableBrowserModule.name]);

export { ngTableModule };
export * from './src/core/index';
export * from './src/browser/index';
