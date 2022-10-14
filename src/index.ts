import { registerPlugin } from '@capacitor/core';

import type { ZipPlugin } from './definitions';

const Zip = registerPlugin<ZipPlugin>('Zip');

export * from './definitions';
export { Zip };
