export function absoluteUrl(path) {
  if (!path) return ''
  if (path.startsWith('http')) return path
  return `${window.location.origin}${path}`
}

export function shareCaption(title) {
  return `I'm building at HH Goa 2026 🏖️⚡\n$ whoami → ${title || 'Builder'}\n\n#FrameInGoa`
}

export function openTwitterIntent({ shareUrl, title }) {
  const params = new URLSearchParams({
    text: shareCaption(title),
    url: absoluteUrl(shareUrl)
  })
  window.open(`https://twitter.com/intent/tweet?${params.toString()}`, '_blank', 'noopener')
}

export async function downloadImage(imageUrl, fileName) {
  const res = await fetch(imageUrl)
  const blob = await res.blob()
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = fileName || 'hhgoa2026-badge.png'
  document.body.appendChild(a)
  a.click()
  a.remove()
  setTimeout(() => URL.revokeObjectURL(url), 4000)
}

export async function copyToClipboard(text) {
  try {
    await navigator.clipboard.writeText(text)
    return true
  } catch {
    return false
  }
}
