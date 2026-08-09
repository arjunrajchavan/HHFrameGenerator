# HH Goa 2026 — Frontend

React + Vite frontend for the Builder Badge generator. Talks to your Spring
Boot backend at `/api/badges` and `/b/{id}`.

## Local development

```bash
npm install
npm run dev
```

This proxies `/api` and `/b` to `http://localhost:8080` (see `vite.config.js`),
so run your Spring Boot app locally on port 8080 alongside this.

## Production build — recommended: one deployable unit

To avoid CORS entirely, have Spring Boot serve the built frontend as static
files. Open `vite.config.js` and change:

```js
build: {
  outDir: 'dist',
  ...
}
```

to point at your Spring Boot project's static resources folder, e.g.:

```js
build: {
  outDir: '../HHframeGenerator/src/main/resources/static',
  emptyOutDir: true
}
```

Then:

```bash
npm run build
```

...and redeploy your Spring Boot jar as usual — the built frontend ships
inside it, one URL for everything.

## Production build — alternative: split deploy

If you'd rather deploy the frontend separately (e.g. Vercel/Netlify) from
the backend (e.g. Railway/Render):

1. Set `VITE_API_BASE_URL` to your backend's deployed URL, e.g.
   `VITE_API_BASE_URL=https://your-backend.up.railway.app`
2. `npm run build` and deploy the `dist/` folder
3. Make sure your Spring Boot backend has CORS enabled for your frontend's
   origin (not included in the code shared so far — add a
   `@CrossOrigin` or a `WebMvcConfigurer` CORS bean if you go this route)

## What's here

- `src/pages/Home.jsx` — the generator: upload photo, name/role fields,
  builder-title shuffle, and a rough client-side preview before you submit
- `src/pages/BadgeView.jsx` — `/badge/:id`, where real visitors land after
  your `/b/{id}` OG page's meta-refresh redirect fires
- `src/components/ResultPanel.jsx` — shown after a badge is generated:
  image, Download, Share to X (via link intent, not direct attach — the
  whole point of the backend is that the shared *link* carries the OG image
  preview), and a copy-link button
- `src/components/PhotoUploader.jsx` — handles JPG/PNG/HEIC (HEIC is
  converted client-side before upload, since Java has no native HEIC
  decoding), shows a cover-fit preview that matches what the backend's
  `BadgeRenderer` will actually produce (center-crop, no client-side pan —
  kept in sync deliberately so the preview never lies about the result)
