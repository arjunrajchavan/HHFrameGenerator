// Same-origin by default (works when Spring Boot serves the built frontend).
// Set VITE_API_BASE_URL if the API is hosted separately, e.g. during
// split-deploy on Vercel + Railway.
const BASE = import.meta.env.VITE_API_BASE_URL || ''

export async function createBadge({ photo, name, role, title, favoriteLanguage }) {
  const form = new FormData()
  if (photo) form.append('photo', photo)
  form.append('name', name || '')
  form.append('role', role || '')
  form.append('title', title || '')
  form.append('favoriteLanguage', favoriteLanguage || '')

  const res = await fetch(`${BASE}/api/badges`, {
    method: 'POST',
    body: form
  })

  if (!res.ok) {
    const text = await safeText(res)
    throw new Error(text || `Request failed with status ${res.status}`)
  }
  return res.json()
}

export async function getBadge(id) {
  const res = await fetch(`${BASE}/api/badges/${id}`)
  if (res.status === 404) {
    throw new NotFoundError(id)
  }
  if (!res.ok) {
    const text = await safeText(res)
    throw new Error(text || `Request failed with status ${res.status}`)
  }
  return res.json()
}

export class NotFoundError extends Error {
  constructor(id) {
    super(`Badge not found: ${id}`)
    this.name = 'NotFoundError'
  }
}

async function safeText(res) {
  try {
    return await res.text()
  } catch {
    return ''
  }
}
