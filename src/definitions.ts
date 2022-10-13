export interface ZipPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
