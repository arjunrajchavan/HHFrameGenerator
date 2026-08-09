import { useRef, useState } from 'react'

function isHeic(file) {
  const name = (file.name || '').toLowerCase()
  return (
    file.type === 'image/heic' ||
    file.type === 'image/heif' ||
    name.endsWith('.heic') ||
    name.endsWith('.heif')
  )
}

function loadHeic2AnyIfNeeded() {
  if (window.heic2any) return Promise.resolve()
  return new Promise((resolve, reject) => {
    const s = document.createElement('script')
    s.src = 'https://cdn.jsdelivr.net/npm/heic2any@0.0.4/dist/heic2any.min.js'
    s.onload = resolve
    s.onerror = reject
    document.head.appendChild(s)
  })
}

export default function PhotoUploader({ onPhotoReady }) {
  const inputRef = useRef(null)
  const [previewUrl, setPreviewUrl] = useState(null)
  const [fileName, setFileName] = useState(null)
  const [busy, setBusy] = useState(false)
  const [error, setError] = useState(null)

  async function handleFile(file) {
    if (!file) return
    setBusy(true)
    setError(null)
    try {
      let outFile = file
      if (isHeic(file)) {
        await loadHeic2AnyIfNeeded()
        const converted = await window.heic2any({ blob: file, toType: 'image/jpeg', quality: 0.92 })
        outFile = new File([converted], file.name.replace(/\.(heic|heif)$/i, '.jpg'), {
          type: 'image/jpeg'
        })
      }
      const url = URL.createObjectURL(outFile)
      setPreviewUrl(url)
      setFileName(outFile.name)
      onPhotoReady(outFile)
    } catch (err) {
      console.error(err)
      setError("Couldn't read that photo — try a JPG or PNG.")
    } finally {
      setBusy(false)
    }
  }

  return (
    <div>
      <label
        className={`upload-box ${previewUrl ? 'has-image' : ''}`}
        onDragOver={(e) => e.preventDefault()}
        onDrop={(e) => {
          e.preventDefault()
          handleFile(e.dataTransfer.files?.[0])
        }}
      >
        {previewUrl ? (
          <div className="upload-preview">
            <img src={previewUrl} alt="Selected preview" />
          </div>
        ) : (
          <div className="upload-placeholder">
            <div className="upload-icon">📸</div>
            <div className="upload-title">{busy ? 'Reading photo…' : 'Drop a photo, or tap to browse'}</div>
            <div className="upload-sub">JPG, PNG, or HEIC from iPhone</div>
          </div>
        )}
        <input
          ref={inputRef}
          type="file"
          accept="image/*,.heic,.heif"
          onChange={(e) => handleFile(e.target.files?.[0])}
          hidden
        />
      </label>
      {previewUrl && (
        <div className="upload-meta">
          <span className="mono-tag">{fileName}</span>
          <button
            type="button"
            className="link-btn"
            onClick={() => inputRef.current?.click()}
          >
            change photo
          </button>
        </div>
      )}
      {error && <div className="field-error">{error}</div>}
    </div>
  )
}
