import { WebPlugin } from '@capacitor/core';

import type { ZipPlugin } from './definitions';

export class ZipWeb extends WebPlugin implements ZipPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
