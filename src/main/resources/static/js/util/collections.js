export function getIndex(list, id) {
    //i = id - 1(если без пропусков, типа 17, 25)
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}