import { useState } from 'react'
import { downloadImage, openTwitterIntent, copyToClipboard, absoluteUrl } from '../shareUtils.js'

export default function ResultPanel({ badge, onReset }) {
  const [copied, setCopied] = useState(false)
  const fileName = `${(badge.name || 'builder').toLowerCase().replace(/[^a-z0-9]+/g, '-')}-hhgoa2026.png`

  async function handleCopy() {
    const ok = await copyToClipboard(absoluteUrl(badge.shareUrl))
    setCopied(ok)
    if (ok) setTimeout(() => setCopied(false), 2000)
  }

  return (
    <div className="result-panel">
      <div className="result-badge-shell">
        <img className="result-image" src={badge.imageUrl} alt={`${badge.name}'s HH Goa 2026 badge`} />
      </div>

      <div className="actions">
        <button
          className="btn btn-primary"
          onClick={() => downloadImage(badge.imageUrl, fileName)}
        >
          ⬇ Download
        </button>
        <button
          className="btn btn-secondary"
          onClick={() => openTwitterIntent({ shareUrl: badge.shareUrl, title: badge.title })}
        >
          𝕏 Share to X
        </button>
      </div>

      <div className="share-link-row">
        <span className="mono-tag truncate">{absoluteUrl(badge.shareUrl)}</span>
        <button type="button" className="link-btn" onClick={handleCopy}>
          {copied ? 'copied ✓' : 'copy link'}
        </button>
      </div>

      {onReset && (
        <button type="button" className="link-btn make-another" onClick={onReset}>
          ← make another badge
        </button>
      )}
    </div>
  )
}
