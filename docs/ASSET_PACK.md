# Asset Pack Support (On-Demand)

## Goal

Move the large `quran-images/` directory (~100MB+) out of the library AAR and into a Play Asset Delivery **on-demand** Asset Pack.

This keeps:
- The feature module (code) small
- Updates that only touch code very small
- Images downloaded only when the user opens the Mushaf

## Structure

```
mushaf-assets/                 # Asset Pack (on-demand)
└── src/main/assets/
    └── quran-images/         # 604 pages × 15 lines of PNGs
```

## Migration steps

1. Move `mushaf-core/src/main/assets/quran-images/` → `mushaf-assets/src/main/assets/quran-images/`
2. In the host app, declare the asset pack (in the base module):

```kotlin
android {
    assetPacks += setOf(":mushaf-assets")
}
```

3. The library image loader will prefer the Asset Pack location when the pack is installed, and fall back to bundled assets otherwise.

## Runtime behavior

- If the Asset Pack is present → load from pack location
- If not yet downloaded → fall back to bundled assets (or show a loading state)
- Host app is responsible for requesting the pack via `AssetPackManager` before (or while) opening the Mushaf

## Notes

- The actual move of the image files is a large git operation. Do it with `git mv` locally.
- Realm database (`quran.realm`) stays in the library for now (smaller and required for the feature module).
- This work is on branch `feature/asset-pack-support`.
