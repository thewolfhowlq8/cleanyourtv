# CleanYourTV 2.0 — Luxury Dark (Phase 1)

This is the first design iteration, maintained separately from `main`.

## Visual direction
- Deep charcoal and near-black background
- Restrained cool-silver highlights
- Frosted glass dock and focused tiles
- Subtle animations suitable for TV remotes
- No changes to TV debloat, accessibility services, or HOME intent handling

## Implementation approach
The existing launcher already supports runtime themes and a translucent cinematic dock. Start by testing the existing theme mechanism before modifying Java views or replacing resources.

## Testing
1. Review the theme configuration and supported keys in `themes/README.md`.
2. Build with the existing `launcher` Gradle project.
3. Verify focus, Back, Home, and app launching with a real TV remote.
4. Test performance and readability at 1080p.

No APK has been built or installed yet.
