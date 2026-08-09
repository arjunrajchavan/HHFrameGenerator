import { useState } from 'react'
import PhotoUploader from '../components/PhotoUploader.jsx'
import GeneratingStatus from '../components/GeneratingStatus.jsx'
import ResultPanel from '../components/ResultPanel.jsx'
import { createBadge } from '../api.js'
import { randomTitle, shuffledTitle } from '../badgeTitles.js'

const STATUS = { IDLE: 'idle', LOADING: 'loading', SUCCESS: 'success', ERROR: 'error' }

export default function Home() {
  const [photo, setPhoto] = useState(null)
  const [name, setName] = useState('')
  const [role, setRole] = useState('')
  const [favoriteLanguage, setFavoriteLanguage] = useState('')
  const [title, setTitle] = useState(() => randomTitle(''))
  const [status, setStatus] = useState(STATUS.IDLE)
  const [errorMsg, setErrorMsg] = useState(null)
  const [badge, setBadge] = useState(null)

  function handleShuffle() {
    setTitle((current) => shuffledTitle(role, current))
  }

  async function handleSubmit(e) {
    e.preventDefault()

    if (!photo) {
      setErrorMsg('Add a photo first — the badge needs a face.')
      return
    }

    // Maximum allowed file size: 1 MB
    const MAX_FILE_SIZE = 1 * 1024 * 1024

    if (photo.size > MAX_FILE_SIZE) {
      setErrorMsg('Photo must be smaller than 1 MB.')
      setStatus(STATUS.ERROR)
      return
    }

    setStatus(STATUS.LOADING)
    setErrorMsg(null)

    try {
      const result = await createBadge({
        photo,
        name,
        role,
        title,
        favoriteLanguage
      })

      setBadge(result)
      setStatus(STATUS.SUCCESS)

    } catch (err) {
      console.error(err)
      setErrorMsg("Couldn't generate your badge — try again in a moment.")
      setStatus(STATUS.ERROR)
    }
  }

  function handleReset() {
    setStatus(STATUS.IDLE)
    setBadge(null)
    setErrorMsg(null)
  }

  return (
    <main className="wrap">
      <section className="intro-col">
        <div className="eyebrow">builder badge generator</div>

        <h1>
          Turn a photo into your{' '}
          <span className="gradient-text">HH Goa 2026</span> badge
        </h1>

        <p className="sub">
          Upload a photo, drop in your name, and get a shareable builder badge in a few
          seconds. No login, no waiting around.
        </p>

        <form className="glass-panel form-panel" onSubmit={handleSubmit}>
          <FieldLabel>Your photo</FieldLabel>

          <PhotoUploader onPhotoReady={setPhoto} />

          <FieldLabel htmlFor="name">Name</FieldLabel>

          <input
            id="name"
            type="text"
            placeholder="e.g. Aisha Rao"
            maxLength={28}
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <FieldLabel htmlFor="role">Stack / role</FieldLabel>

          <input
            id="role"
            type="text"
            placeholder="e.g. Full-Stack, Design, ML"
            maxLength={24}
            value={role}
            onChange={(e) => setRole(e.target.value)}
          />

          <FieldLabel htmlFor="lang">Favorite language</FieldLabel>

          <input
            id="lang"
            type="text"
            placeholder="e.g. TypeScript"
            maxLength={20}
            value={favoriteLanguage}
            onChange={(e) => setFavoriteLanguage(e.target.value)}
          />

          <FieldLabel>Builder title</FieldLabel>

          <div className="title-row">
            <input
              type="text"
              readOnly
              value={title}
            />

            <button
              type="button"
              className="shuffle-btn"
              onClick={handleShuffle}
              title="Shuffle title"
            >
              🎲
            </button>
          </div>

          {errorMsg && (
            <div className="field-error">
              {errorMsg}
            </div>
          )}

          <button
            className="btn btn-primary btn-generate"
            type="submit"
            disabled={status === STATUS.LOADING}
          >
            {status === STATUS.LOADING
              ? 'Generating…'
              : 'Generate badge'}
          </button>
        </form>
      </section>

      <section className="preview-col">
        <div className="canvas-shell">

          {status === STATUS.LOADING && (
            <GeneratingStatus />
          )}

          {status === STATUS.SUCCESS && badge && (
            <ResultPanel
              badge={badge}
              onReset={handleReset}
            />
          )}

          {(status === STATUS.IDLE || status === STATUS.ERROR) && (
            <EmptyPreview
              photo={photo}
              name={name}
              role={role}
              title={title}
            />
          )}

        </div>
      </section>
    </main>
  )
}

function FieldLabel({ children, htmlFor }) {
  return (
    <label
      className="field-label"
      htmlFor={htmlFor}
    >
      {children}
    </label>
  )
}

function EmptyPreview({ photo, name, role, title }) {
  const previewUrl = photo
    ? URL.createObjectURL(photo)
    : null

  return (
    <div className="empty-preview">

      <div className="badge-mock">

        <div className="badge-mock-header">
          <div className="badge-mock-eyebrow">
            HACKER HOUSE
          </div>

          <div className="badge-mock-title">
            GOA 2026
          </div>
        </div>

        <div className="badge-mock-photo">
          {previewUrl ? (
            <img
              src={previewUrl}
              alt="Your photo preview"
            />
          ) : (
            <span className="badge-mock-placeholder">
              your photo
            </span>
          )}
        </div>

        <div className="badge-mock-name">
          {name || 'Your Name'}
        </div>

        {role && (
          <div className="badge-mock-pill">
            {role.toUpperCase()}
          </div>
        )}

        <div className="badge-mock-terminal">
          <span className="mono-dim">
            $ whoami
          </span>

          <br />

          <span className="mono-gold">
            &gt; {title}
          </span>
        </div>

        <div className="badge-mock-hashtag">
          #FrameInGoa
        </div>

      </div>

      <div className="drag-hint">
        This is a rough preview — the real badge is rendered server-side.
      </div>

    </div>
  )
}