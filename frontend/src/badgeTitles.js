const ROLE_TEMPLATES = [
  '{role} Whisperer',
  'Chief {role} Officer, Beach Division',
  '10x {role}, Sunburnt Edition',
  '{role} Shaman',
  'Senior Vibes {role}',
  '{role} on Goa Time',
  'The {role} Who Ships',
  '{role}, Certified Coconut-Powered',
  'Full-Send {role}',
  '{role} of the Tides',
  'Professional {role}, Unofficially'
]

const GENERIC_TITLES = [
  'Full-Stack Beach Bum',
  'Professional Ctrl+S Presser',
  'Debug Ninja',
  'Prompt Whisperer',
  'Ship-It Shaman',
  '10x Vibes Coder',
  'Ctrl+Alt+Chill',
  'Ambient Hacker',
  'The Merge Conflict Whisperer',
  'Night-Build Nomad',
  'Chief Vibe Officer',
  'API Alchemist',
  'Sunburnt Systems Architect',
  'Founder of One (1) Idea'
]

export function randomTitle(role) {
  const trimmed = (role || '').trim()
  if (trimmed) {
    const t = ROLE_TEMPLATES[Math.floor(Math.random() * ROLE_TEMPLATES.length)]
    return t.replace('{role}', trimmed)
  }
  return GENERIC_TITLES[Math.floor(Math.random() * GENERIC_TITLES.length)]
}

export function shuffledTitle(role, current) {
  let next = randomTitle(role)
  let guard = 0
  while (next === current && guard < 6) {
    next = randomTitle(role)
    guard++
  }
  return next
}
