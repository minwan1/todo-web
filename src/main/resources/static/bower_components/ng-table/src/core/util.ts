import { GroupingPartial, GroupingFunc } from './grouping/index';
import { SortingValues } from './sorting/index';

/**
 * @private
 */
export function convertSortToOrderBy(sorting: SortingValues) {
    const result: string[] = [];
    for (let column in sorting) {
        result.push((sorting[column] === "asc" ? "+" : "-") + column);
    }
    return result;
}

/**
 * @private
 */
export function isGroupingFun(val: string | GroupingPartial<any>): val is GroupingFunc<any> {
    return typeof val === 'function'
}
