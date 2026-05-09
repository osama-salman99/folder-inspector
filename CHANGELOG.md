# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Multi-OS release pipeline that publishes jpackage installers (`.dmg`, `.deb`, `.msi`),
  the shaded JAR, and a sources archive to GitHub Releases on `v*` tag pushes.
- `CHANGELOG.md` following the Keep a Changelog format.

## [1.0.0] - 2026-05-09

### Added

- Initial public release of Folder Inspector, a JavaFX desktop tool for
  inspecting folder contents and sizes.
- Path validation rules for selected directories.
- Toolbar icons for copy, file, folder, and link actions.
- Unit tests for `Container`, `DirectorySizeCalculator`, and `DigitalFormatter`.
- Continuous integration workflow that builds and tests pull requests on `main`.

[Unreleased]: https://github.com/osama-salman99/folder-inspector/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/osama-salman99/folder-inspector/releases/tag/v1.0.0
