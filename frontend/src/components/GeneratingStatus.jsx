import { useEffect, useState } from 'react'

const STEPS = [
  '$ reading photo…',
  '$ cropping frame…',
  '$ rendering badge.png…',
  '$ uploading to cdn…',
  '$ almost there…'
]

export default function GeneratingStatus() {
  const [step, setStep] = useState(0)

  useEffect(() => {
    const id = setInterval(() => {
      setStep((s) => Math.min(s + 1, STEPS.length - 1))
    }, 750)
    return () => clearInterval(id)
  }, [])

  return (
    <div className="terminal-status">
      <div className="terminal-dots">
        <span className="dot dot-coral" />
        <span className="dot dot-gold" />
        <span className="dot dot-palm" />
      </div>
      <div className="terminal-line">
        {STEPS[step]}
        <span className="cursor-blink">▌</span>
      </div>
    </div>
  )
}
