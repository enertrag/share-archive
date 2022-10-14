
export interface ZipOptions {

  /**
   * The uri of the source directory.
   */
  sourceDir: string;

  /**
   * The name of the destination file.
   */
  destFilename: string;
}

export interface ZipResult {

  /**
   * true if the file was successfully saved, false otherwise
   */
  success: boolean;
}

export interface Available {

  /**
   * true if available, false otherwise
   */
  available: boolean;
}

export interface ZipPlugin {

  /**
   * Checks if the functionality is available on the current platform.
   */
  isAvailable(): Promise<Available>;

  /**
   * Creates a zip archive including the content of the specified directory.
   * The created file will be saved/shared via the os's mechanisms.
   */
  shareArchive(options: ZipOptions): Promise<ZipResult>;
}
